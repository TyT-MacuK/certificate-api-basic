package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.EntityNotFoundException;

import java.util.List;

public interface TagService extends BaseService<Long, String, TagDto> {

    List<GiftCertificateDto> findTagCertificates(Long id) throws EntityNotFoundException;
}
