package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

/**
 * The interface Tag dao.
 */
public interface TagDao extends BaseDao<Long, String, Tag> {

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
