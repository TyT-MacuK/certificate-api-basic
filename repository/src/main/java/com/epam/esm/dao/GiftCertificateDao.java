package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;

/**
 * The interface Gift certificate dao.
 */
public interface GiftCertificateDao extends CrudDao<Long, GiftCertificate> {

    /**
     * Find by params.
     *
     * @param tagNames               the tag names
     * @param certificateName        the certificate name
     * @param certificateDescription the certificate description
     * @param orderByName            the order by name
     * @param orderByCreateDate      the order by create date
     * @param pageNumber             the page number
     * @param pageSize               the page size
     * @return the list
     */
    List<GiftCertificate> findByParams(List<String> tagNames, String certificateName, String certificateDescription,
                                  String orderByName, String orderByCreateDate, int pageNumber, int pageSize);
}
