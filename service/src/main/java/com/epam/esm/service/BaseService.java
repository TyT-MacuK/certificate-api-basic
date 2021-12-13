package com.epam.esm.service;

import com.epam.esm.dto.AbstractDto;
import com.epam.esm.exception.EntityAlreadyExistsException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.InvalidEntityDataException;

import java.util.List;

public interface BaseService<K, N, T extends AbstractDto> {
    boolean add(T t) throws InvalidEntityDataException, EntityAlreadyExistsException;

    T findById(K id) throws EntityNotFoundException;

    List<T> findByPartOfName(N partOfName);

    boolean delete(K id) throws EntityNotFoundException;
}
