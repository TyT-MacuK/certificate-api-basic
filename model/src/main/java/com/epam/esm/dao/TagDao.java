package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

/**
 * The interface Tag dao.
 */
public interface TagDao extends BaseDao<Long, String, Tag> {

    Optional<Tag> findByName(String name);

    List<GiftCertificate> findTagCertificates(Long id);

    boolean detachAllCertificates(Long tagId);
}
