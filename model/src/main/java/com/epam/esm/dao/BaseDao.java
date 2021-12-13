package com.epam.esm.dao;

import com.epam.esm.entity.AbstractEntity;

import java.util.List;
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
     * Find by part of name list.
     *
     * @param partOfName the part of name
     * @return the list
     */
    List<T> findByPartOfName(N partOfName);

    /**
     * Delete boolean.
     *
     * @param id the key
     * @return the boolean
     */
    boolean delete(K id);
}
