package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateSearchParamsDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.AttachException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.InvalidEntityDataException;

import java.util.List;
import java.util.Map;

/**
 * The interface Gift certificate service.
 */
public interface GiftCertificateService extends BaseService <Long, GiftCertificateDto> {

    /**
     * Find all gift certificates with tags.
     *
     * @return the map
     */
    List<GiftCertificateDto> findByParams(GiftCertificateSearchParamsDto searchParams) throws InvalidEntityDataException;

    /**
     * Update gift certificate.
     *
     * @param certificate the certificate
     * @return the boolean
     * @throws EntityNotFoundException    the entity not found exception
     * @throws InvalidEntityDataException the invalid entity data exception
     */
    boolean updateGiftCertificate(GiftCertificateDto certificate) throws EntityNotFoundException, InvalidEntityDataException;
}
