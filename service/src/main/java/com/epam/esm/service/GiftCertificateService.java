package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.InvalidEntityDataException;
import com.epam.esm.exception.InvalidSortOderNameException;

import java.util.List;

/**
 * The interface Gift certificate service.
 */
public interface GiftCertificateService extends BaseService <Long, String, GiftCertificateDto> {

    /**
     * Find all attach tags to certificate.
     *
     * @param giftCertificateId the gift certificate id
     * @return the list
     * @throws EntityNotFoundException the entity not found exception
     */
    List<TagDto> findCertificateTags(Long giftCertificateId) throws EntityNotFoundException;

    /**
     * Find all certificates.
     *
     * @param sortOrder the sort order
     * @return the list
     * @throws InvalidSortOderNameException the invalid sort oder name exception
     */
    List<GiftCertificateDto> sortCertificate(String sortOrder) throws InvalidSortOderNameException;

    /**
     * Update gift certificate.
     *
     * @param certificate the certificate
     * @return the boolean
     * @throws EntityNotFoundException    the entity not found exception
     * @throws InvalidEntityDataException the invalid entity data exception
     */
    boolean updateGiftCertificate(GiftCertificateDto certificate) throws EntityNotFoundException, InvalidEntityDataException;

    /**
     * Attach tag and gift certificate.
     *
     * @param giftCertificateId the gift certificate id
     * @param tagId             the tag id
     * @return the boolean
     * @throws EntityNotFoundException the entity not found exception
     */
    boolean attach(Long giftCertificateId, Long tagId) throws EntityNotFoundException;
}
