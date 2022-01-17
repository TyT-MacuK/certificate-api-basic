package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

@Repository
public class TagDaoImpl extends AbstractDao<Long, Tag> implements TagDao {
    private static final String FIND_MOST_WIDELY_USED_TAG = """
            SELECT tag.id, tag.name FROM tag
            JOIN gift_certificate_has_tag ON gift_certificate_has_tag.tag_id = tag.id
            JOIN gift_certificate ON gift_certificate.id = gift_certificate_has_tag.gift_certificate_id
            JOIN orders ON orders.gift_certificate_id = gift_certificate.id
            JOIN user ON user.id = orders.user_id
            WHERE user.id = :id
            GROUP BY orders.gift_certificate_id
            ORDER BY COUNT(*) DESC LIMIT 1
            """;

    private static final String ID_PARAM = "id";

    private EntityManager entityManager;

    @Autowired
    public TagDaoImpl(EntityManager entityManager) {
        super(entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Tag> findMostWidelyUsedTag(Long userId) {
        Tag result;
        try {
            result = (Tag) entityManager.createNativeQuery(FIND_MOST_WIDELY_USED_TAG, Tag.class)
                    .setParameter(ID_PARAM, userId).getSingleResult();
        } catch (NoResultException e) {
            return Optional.empty();
        }

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<Tag> findByName(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = criteriaQuery.from(Tag.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("name"), name));
        Tag result;
        try {
            result = entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException e) {
            return Optional.empty();
        }
        return Optional.ofNullable(result);
    }

    @Override
    protected Class<Tag> getIdentityClass() {
        return Tag.class;
    }
}