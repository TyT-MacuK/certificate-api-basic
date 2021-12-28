package com.epam.esm.dao.impl;

import com.epam.esm.dao.CrudDao;
import com.epam.esm.entity.AbstractEntity;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class AbstractDao<K, T extends AbstractEntity> implements CrudDao<K, T> {
    private final EntityManager entityManager;

    @Override
    public void add(T t) {
        entityManager.persist(t);
    }

    @Override
    public void update(T t) {
        entityManager.merge(t);
    }

    @Override
    public Optional<T> findById(K id) {
        return Optional.ofNullable(entityManager.find(getIdentityClass(), id));
    }

    @Override
    public List<T> findAll(int page, int pageSize) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> entityCriteriaQuery = criteriaBuilder.createQuery(getIdentityClass());
        Root<T> root = entityCriteriaQuery.from(getIdentityClass());
        CriteriaQuery<T> select = entityCriteriaQuery.select(root);
        return entityManager.createQuery(select)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    @Override
    public void delete(T t) {
        entityManager.remove(t);
    }

    protected abstract Class<T> getIdentityClass();
}
