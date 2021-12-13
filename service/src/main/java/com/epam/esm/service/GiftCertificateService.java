package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.InvalidEntityDataException;
import com.epam.esm.exception.InvalidSortOderNameException;

import java.util.List;

public interface GiftCertificateService extends BaseService <Long, String, GiftCertificateDto> {

    List<TagDto> findCertificateTags(Long giftCertificateId) throws EntityNotFoundException;

    List<GiftCertificateDto> sortCertificate(String sortOrder) throws InvalidSortOderNameException;

    boolean updateGiftCertificate(GiftCertificateDto certificate) throws EntityNotFoundException, InvalidEntityDataException;

    boolean attach(Long giftCertificateId, Long tagId) throws EntityNotFoundException;
}
