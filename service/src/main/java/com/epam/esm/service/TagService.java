package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.EntityNotFoundException;

import java.util.List;

/**
 * The interface Tag service.
 */
public interface TagService extends BaseService<Long, TagDto> {

    /**
     * Find all tags.
     *
     * @return the list
     */
    List<TagDto> findAll();

    /**
     * Find by part of name list.
     *
     * @param partOfName the part of name
     * @return the list
     */
    List<TagDto> findByPartOfName(String partOfName);

    /**
     * Find all attach gift certificates to tag.
     *
     * @param id the id
     * @return the list
     * @throws EntityNotFoundException the entity not found exception
     */
    List<GiftCertificateDto> findTagCertificates(Long id) throws EntityNotFoundException;
}
