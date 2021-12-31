package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateSearchParamsDto;
import com.epam.esm.exception.EntityAlreadyExistsException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.InvalidEntityDataException;

import java.util.List;

/**
 * The interface Gift certificate service.
 */
public interface GiftCertificateService {

    /**
     * Add gift certificate.
     *
     * @param giftCertificate the gift certificate
     * @throws InvalidEntityDataException   the invalid entity data exception
     * @throws EntityAlreadyExistsException the entity already exists exception
     */
    void add(GiftCertificateDto giftCertificate) throws InvalidEntityDataException, EntityAlreadyExistsException;

    /**
     * Find by id gift certificate.
     *
     * @param id the id
     * @return the gift certificate dto
     * @throws EntityNotFoundException the entity not found exception
     */
    GiftCertificateDto findById(long id) throws EntityNotFoundException;

    /**
     * Find all gift certificate.
     *
     * @param pageNumber the page number
     * @param pageSize   the page size
     * @return the list of gift certificates
     */
    List<GiftCertificateDto> findAll(int pageNumber, int pageSize);


    /**
     * Find gift certificates by params.
     *
     * @param searchParams the search params
     * @param pageNumber   the page number
     * @param pageSize     the page size
     * @return the list
     * @throws InvalidEntityDataException the invalid entity data exception
     */
    List<GiftCertificateDto> findByParams(GiftCertificateSearchParamsDto searchParams, int pageNumber, int pageSize)
            throws InvalidEntityDataException;

    /**
     * Update gift certificate.
     *
     * @param certificate the certificate
     * @return the boolean
     * @throws EntityNotFoundException    the entity not found exception
     * @throws InvalidEntityDataException the invalid entity data exception
     */
    void updateGiftCertificate(GiftCertificateDto certificate) throws EntityNotFoundException, InvalidEntityDataException;

    /**
     * Delete boolean.
     *
     * @param id the id
     * @throws EntityNotFoundException the entity not found exception
     */
    void delete(long id) throws EntityNotFoundException;
}
