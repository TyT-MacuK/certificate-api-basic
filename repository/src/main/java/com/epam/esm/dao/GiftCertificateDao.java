package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import java.util.List;

/**
 * The interface Gift certificate dao.
 */
public interface GiftCertificateDao extends BaseDao <Long, GiftCertificate> {

    List<GiftCertificate> findByParams(String tagName, String certificateName, String certificateDescription,
                                  String orderByName, String orderByCreateDate);

    /**
     * Find all attach tags to certificate.
     *
     * @param giftCertificateId the gift certificate id
     * @return the list
     */
    List<Tag> findCertificateTags(Long giftCertificateId);

    /**
     * Update giftCertificate.
     *
     * @param certificate the certificate
     * @return the boolean
     */
    boolean update(GiftCertificate certificate);

    /**
     * Detach all tags.
     *
     * @param giftCertificateId the gift certificate id
     * @return the boolean
     */
    boolean detachAllTags(Long giftCertificateId);

    /**
     * Attach tag and gift certificate.
     *
     * @param giftCertificateId the gift certificate id
     * @param tagId             the tag id
     * @return the boolean
     */
    boolean attach(Long giftCertificateId, Long tagId);
}
