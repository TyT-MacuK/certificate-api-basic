package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

/**
 * The interface Tag dao.
 */
public interface TagDao extends BaseDao<Long, Tag> {

    /**
     * Find all tags.
     *
     * @return the list
     */
    List<Tag> findAll();

    /**
     * Find by part of name.
     *
     * @param partOfName the part of name
     * @return the list
     */
    List<Tag> findByPartOfName(String partOfName);

    /**
     * Find by name.
     *
     * @param name the name
     * @return the optional
     */
    Optional<Tag> findByName(String name);

    /**
     * Find all attach gift certificates to tag.
     *
     * @param id the id
     * @return the list
     */
    List<GiftCertificate> findTagCertificates(Long id);

    /**
     * Detach all certificates.
     *
     * @param tagId the tag id
     * @return the boolean
     */
    boolean detachAllCertificates(Long tagId);
}
