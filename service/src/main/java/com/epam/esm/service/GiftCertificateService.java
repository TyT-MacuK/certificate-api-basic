package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exception.InvalidSortOderNameException;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateService extends BaseService <Long, String, GiftCertificateDto> {
    Optional<GiftCertificateDto> findByTagName(String tagName);

    List<GiftCertificateDto> findByPartOfTagName(String partOfTagName);

    List<GiftCertificateDto> sortCertificate(String sortOrder) throws InvalidSortOderNameException;

    boolean updateGiftCertificate(GiftCertificateDto certificate);
}
