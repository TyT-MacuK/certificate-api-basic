package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;
import java.util.Optional;

/**
 * The interface Gift certificate dao.
 */
public interface GiftCertificateDao extends BaseDao <Long, String, GiftCertificate> {
    /**
     * Find by tag name optional.
     *
     * @param tagName the tag name
     * @return the optional
     */
    Optional<GiftCertificate> findByTagName(String tagName);

    /**
     * Find by part of tag name list.
     *
     * @param partOfTagName the part of tag name
     * @return the list
     */
    List<GiftCertificate> findByPartOfTagName(String partOfTagName);

    /**
     * Find all certificate list.
     *
     * @return the list
     */
    List<GiftCertificate> sortCertificate(String sortOrder);

    /**
     * Update name boolean.
     *
     * @param certificate the certificate
     * @return the boolean
     */
    boolean updateName(GiftCertificate certificate);
}
