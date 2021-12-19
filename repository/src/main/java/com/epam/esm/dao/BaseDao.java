package com.epam.esm.dao;

import com.epam.esm.entity.AbstractEntity;

import java.util.Optional;

/**
 * The interface Base dao.
 *
 * @param <K> the key type
 * @param <T> the generic type
 */
public interface BaseDao<K, T extends AbstractEntity> {
    /**
     * Add entity.
     *
     * @param t the entity
     * @return the boolean
     */
    boolean add(T t);

    /**
     * Find by id.
     *
     * @param id the id
     * @return the optional
     */
    Optional<T> findById(K id);

    /**
     * Delete object.
     *
     * @param id the entity id
     * @return the boolean
     */
    boolean delete(K id);
}
