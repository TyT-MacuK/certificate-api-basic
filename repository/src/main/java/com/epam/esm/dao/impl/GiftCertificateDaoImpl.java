package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private static final String ID_PARAM = "id";
    private static final String NAME_PARAM = "name";
    private static final String DESCRIPTION_PARAM = "description";
    private static final String TAGS_PARAM = "tags";

    private final EntityManager entityManager;

    @Override
    public void add(GiftCertificate certificate) {
        entityManager.persist(certificate);
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        return Optional.ofNullable(entityManager.find(GiftCertificate.class, id));
    }

    @Override
    public void delete(GiftCertificate certificate) {
        entityManager.remove(certificate);
    }

    @Override
    public List<GiftCertificate> findByParams(List<String> tagNames, String certificateName,
                                              String certificateDescription, String orderByName, String orderByCreateDate) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> query = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = query.from(GiftCertificate.class);

        List<Predicate> predicates = createCertificatePredicate(criteriaBuilder, root, certificateName,
                certificateDescription);
        if (predicates != null) {
            Predicate resultPredicate = predicates.get(0);
            for (Predicate predicate : predicates) {
                resultPredicate = criteriaBuilder.and(resultPredicate, predicate);
            }
            query.where(resultPredicate);
        }

        Optional<Predicate> tagOptionalPredicate = createTagsPredicate(criteriaBuilder, root, tagNames);
        tagOptionalPredicate.ifPresent(query::where);

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public GiftCertificate update(GiftCertificate certificate) {
        return entityManager.merge(certificate);
    }

    private List<Predicate> createCertificatePredicate(CriteriaBuilder criteriaBuilder, Root<GiftCertificate> root,
                                                       String certificateName, String certificateDescription) {
        List<Predicate> predicates = new ArrayList<>();
        if (certificateName != null) {
            predicates.add(criteriaBuilder.like(root.get(NAME_PARAM), "%" + certificateName + "%"));
        }
        if (certificateDescription != null) {
            predicates.add(criteriaBuilder.like(root.get(DESCRIPTION_PARAM), "%" + certificateDescription + "%"));
        }
        return predicates;
    }

    private Optional<Predicate> createTagsPredicate(CriteriaBuilder criteriaBuilder, Root<GiftCertificate> certificateRoot,
                                                    List<String> tagNames) {
        Predicate result = null;
        if (tagNames != null) {
            CriteriaQuery<Tag> queryTag = criteriaBuilder.createQuery(Tag.class);
            Root<Tag> tagRoot = queryTag.from(Tag.class);
            Predicate resultPredicate;
            if (tagNames.size() != 0) {
                List<Predicate> tagPredicates = new ArrayList<>();
                for (String tagName : tagNames) {
                    tagPredicates.add(criteriaBuilder.like(tagRoot.get(NAME_PARAM), "%" + tagName + "%"));
                }
                resultPredicate = tagPredicates.get(0);
                for (Predicate predicate : tagPredicates) {
                    resultPredicate = criteriaBuilder.or(resultPredicate, predicate);
                }
                queryTag.where(resultPredicate);
                List<Tag> suitableTags = entityManager.createQuery(queryTag).getResultList();

                if (suitableTags.size() != 0) {
                    result = criteriaBuilder.isMember(suitableTags.get(0), certificateRoot.get(TAGS_PARAM));
                } else {
                    result = criteriaBuilder.disjunction();
                }
                for (Tag tag : suitableTags) {
                    Predicate memberPredicate = criteriaBuilder.isMember(tag, certificateRoot.get(TAGS_PARAM));
                    result = criteriaBuilder.and(result, memberPredicate);
                }
            }
        }
        return Optional.ofNullable(result);
    }
}