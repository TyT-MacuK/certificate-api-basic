package com.epam.esm.service;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.EntityAlreadyExistsException;
import com.epam.esm.exception.EntityNotFoundException;

import java.util.List;

/**
 * The interface Tag service.
 */
public interface TagService {
    /**
     * Add tag.
     *
     * @param tag the tag
     * @throws EntityAlreadyExistsException the entity already exists exception
     */
    void add(TagDto tag) throws EntityAlreadyExistsException;

    /**
     * Find tag by id.
     *
     * @param id the id
     * @return the tag dto
     * @throws EntityNotFoundException the entity not found exception
     */
    TagDto findById(long id) throws EntityNotFoundException;

    /**
     * Find all tags.
     *
     * @param page     the page
     * @param pageSize the page size
     * @return the list
     */
    List<TagDto> findAll(int page, int pageSize);

    /**
     * Find most widely used tag.
     *
     * @param userId the user id
     * @return the tag dto
     * @throws EntityNotFoundException the entity not found exception
     */
    TagDto findMostWidelyUsedTag(Long userId) throws EntityNotFoundException;

    /**
     * Delete tag.
     *
     * @param id the id
     * @throws EntityNotFoundException the entity not found exception
     */
    void delete(long id) throws EntityNotFoundException;
}
