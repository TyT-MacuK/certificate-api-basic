package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

/**
 * The interface Gift certificate dao.
 */
public interface GiftCertificateDao extends BaseDao <Long, String, GiftCertificate> {

    List<Tag> findCertificateTags(Long giftCertificateId);

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
    boolean update(GiftCertificate certificate);

    boolean detachAllTags(Long giftCertificateId);

    boolean attach(Long giftCertificateId, Long tagId);
}
