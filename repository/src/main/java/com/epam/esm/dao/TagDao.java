package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.Optional;

/**
 * The interface Tag dao.
 */
public interface TagDao extends CrudDao<Long, Tag> {
    /**
     * Find by name.
     *
     * @param name the name
     * @return the optional tag
     */
    Optional<Tag> findByName(String name);

    /**
     * Find most widely used tag optional.
     *
     * @param userId the user id
     * @return the optional tag
     */
    Optional<Tag> findMostWidelyUsedTag(Long userId);
}
