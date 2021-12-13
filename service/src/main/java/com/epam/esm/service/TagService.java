package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.EntityNotFoundException;

import java.util.List;

/**
 * The interface Tag service.
 */
public interface TagService extends BaseService<Long, String, TagDto> {

    /**
     * Find all attach gift certificates to tag.
     *
     * @param id the id
     * @return the list
     * @throws EntityNotFoundException the entity not found exception
     */
    List<GiftCertificateDto> findTagCertificates(Long id) throws EntityNotFoundException;
}
