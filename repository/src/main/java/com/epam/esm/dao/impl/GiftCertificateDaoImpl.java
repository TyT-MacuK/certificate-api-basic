package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class GiftCertificateDaoImpl extends AbstractDao<Long, GiftCertificate> implements GiftCertificateDao {
    private static final String NAME_PARAM = "name";
    private static final String DESCRIPTION_PARAM = "description";
    private static final String ASCENDING_PARAM = "ASC";
    private static final String DESCENDING_PARAM = "DESC";

    private EntityManager entityManager;

    @Autowired
    public GiftCertificateDaoImpl(EntityManager entityManager) {
        super(entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public List<GiftCertificate> findByParams(List<String> tagNames, String certificateName,
                                              String certificateDescription, String orderByName,
                                              String orderByCreateDate, int pageNumber, int pageSize) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> query = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = query.from(GiftCertificate.class);

        List<Predicate> predicates = createCertificatePredicate(criteriaBuilder, root, certificateName,
                certificateDescription);
        if (predicates != null && !predicates.isEmpty()) {
            Predicate resultPredicate = predicates.get(0);
            for (Predicate predicate : predicates) {
                resultPredicate = criteriaBuilder.and(resultPredicate, predicate);
            }
            query.where(resultPredicate);
        }

        Optional<Predicate> tagOptionalPredicate = createTagsPredicate(criteriaBuilder, root, tagNames);
        tagOptionalPredicate.ifPresent(query::where);

        List<Order> requestOrders = createOrderingPredicate(criteriaBuilder, root, orderByName, orderByCreateDate);
        requestOrders.stream().map(query::orderBy).toList();
        return entityManager.createQuery(query)
                .setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    @Override
    protected Class<GiftCertificate> getIdentityClass() {
        return GiftCertificate.class;
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
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Predicate result = null;
        List<Predicate> tagPredicates = new ArrayList<>();
        if (tagNames != null && !tagNames.isEmpty()) {
            tagNames.stream().map(tagName -> tagPredicates.add(criteriaBuilder.equal(
                    certificateRoot.join("tags").get("name"), tagName))).toList();
            result = tagPredicates.get(0);
                for (Predicate predicate : tagPredicates) {
                    result = criteriaBuilder.or(result, predicate);
                }
            criteriaQuery.where(result);
        }
        return Optional.ofNullable(result);
    }

    private List<Order> createOrderingPredicate(CriteriaBuilder criteriaBuilder, Root<GiftCertificate> certificateRoot,
                                                String orderByName, String orderByCreateDate) {
        List<Order> requestOrders = new ArrayList<>();
        if (orderByName != null) {
            if (orderByName.equalsIgnoreCase(ASCENDING_PARAM)) {
                requestOrders.add(criteriaBuilder.asc(certificateRoot.get(NAME_PARAM)));
            } else if (orderByName.equalsIgnoreCase(DESCENDING_PARAM)) {
                requestOrders.add(criteriaBuilder.desc(certificateRoot.get(NAME_PARAM)));
            }
        }
        if (orderByCreateDate != null) {
            if (orderByCreateDate.equalsIgnoreCase(ASCENDING_PARAM)) {
                requestOrders.add(criteriaBuilder.asc(certificateRoot.get(DESCRIPTION_PARAM)));
            } else if (orderByCreateDate.equalsIgnoreCase(DESCENDING_PARAM)) {
                requestOrders.add(criteriaBuilder.desc(certificateRoot.get(DESCRIPTION_PARAM)));
            }
        }
        return requestOrders;
    }
}