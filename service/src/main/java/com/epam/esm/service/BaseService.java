package com.epam.esm.service;

import com.epam.esm.dto.AbstractDto;
import com.epam.esm.exception.EntityAlreadyExistsException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.InvalidEntityDataException;

import java.util.List;

/**
 * The interface Base service.
 *
 * @param <K> the key type
 * @param <N> the name type
 * @param <T> the generic type
 */
public interface BaseService<K, N, T extends AbstractDto> {
    /**
     * Add entity.
     *
     * @param t the entity
     * @return the boolean
     * @throws InvalidEntityDataException   the invalid entity data exception
     * @throws EntityAlreadyExistsException the entity already exists exception
     */
    boolean add(T t) throws InvalidEntityDataException, EntityAlreadyExistsException;

    /**
     * Find entity by id.
     *
     * @param id the id
     * @return the entity
     * @throws EntityNotFoundException the entity not found exception
     */
    T findById(K id) throws EntityNotFoundException;

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
     * @param id the id
     * @return the boolean
     * @throws EntityNotFoundException the entity not found exception
     */
    boolean delete(K id) throws EntityNotFoundException;
}
