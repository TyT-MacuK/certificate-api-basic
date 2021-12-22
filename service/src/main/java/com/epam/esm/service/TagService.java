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
}
