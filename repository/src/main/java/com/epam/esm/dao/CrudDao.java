package com.epam.esm.dao;

import com.epam.esm.entity.AbstractEntity;

import java.util.List;
import java.util.Optional;

/**
 * The interface Base dao.
 *
 * @param <K> the key type
 * @param <T> the generic type extends AbstractEntity
 */
public interface CrudDao<K, T extends AbstractEntity> {
    /**
     * Add entity.
     *
     * @param t the entity
     */
    void add(T t);

    /**
     * Update entity
     *
     * @param t the entity
     */
    void update(T t);

    /**
     * Find by id.
     *
     * @param id the id
     * @return the optional entity
     */
    Optional<T> findById(K id);

    /**
     * Find all.
     *
     * @param page     the page
     * @param pageSize the page size
     * @return the list
     */
    List<T> findAll(int page, int pageSize);

    /**
     * Delete entity
     *
     * @param t the entity
     */
    void delete(T t);
}
