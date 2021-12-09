package com.epam.esm.dao;

import com.epam.esm.entity.AbstractEntity;

import java.util.Optional;

/**
 * The interface Base dao.
 *
 * @param <K> the key type
 * @param <N> the name type
 * @param <T> the generic type
 */
public interface BaseDao<K, N, T extends AbstractEntity> {
    /**
     * Add boolean.
     *
     * @param t the entity
     * @return the boolean
     */
    boolean add(T t);

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     */
    Optional<T> findById(K id);

    /**
     * Find by name optional.
     *
     * @param name the name
     * @return the optional
     */
    Optional<T> findByName(N name);

    /**
     * Delete boolean.
     *
     * @param id the key
     * @return the boolean
     */
    boolean delete(K id);
}
