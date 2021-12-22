package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import java.util.List;

/**
 * The interface Gift certificate dao.
 */
public interface GiftCertificateDao extends BaseDao <Long, GiftCertificate> {

    List<GiftCertificate> findByParams(List<String> tagNames, String certificateName, String certificateDescription,
                                  String orderByName, String orderByCreateDate);

    /**
     * Update giftCertificate.
     *
     * @param certificate the certificate
     * @return the GiftCertificate
     */
    GiftCertificate update(GiftCertificate certificate);
}
