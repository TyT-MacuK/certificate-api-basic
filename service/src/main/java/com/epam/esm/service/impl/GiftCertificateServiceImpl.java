package com.epam.esm.service.impl;

import com.epam.esm.converter.GiftCertificateConverter;
import com.epam.esm.converter.TagConverter;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateSearchParamsDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.InvalidEntityDataException;
import com.epam.esm.exception.TypeOfValidationError;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.GiftCertificateValidator;
import com.epam.esm.validator.SearchParamsValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateDao certificateDao;
    private final GiftCertificateValidator certificateValidator;
    private final SearchParamsValidator searchParamsValidator;
    private final GiftCertificateConverter certificateConverter;
    private final TagConverter tagConverter;

    @Override
    @Transactional
    public void add(GiftCertificateDto giftCertificateDto) throws InvalidEntityDataException {
        List<TypeOfValidationError> errors = certificateValidator.isGiftCertificateValid(giftCertificateDto);
        if (!errors.isEmpty()) {
            throw new InvalidEntityDataException(errors, GiftCertificate.class);
        }
        GiftCertificate certificate = certificateConverter.convertToEntity(giftCertificateDto);
        certificateDao.add(certificate);
    }

    @Override
    public List<GiftCertificateDto> findAll(int pageNumber, int pageSize) {
        List<GiftCertificate> certificateList = certificateDao.findAll(pageNumber, pageSize);
        return certificateList.stream().map(certificateConverter::convertToDto).toList();
    }

    @Override
    public GiftCertificateDto findById(long id) throws EntityNotFoundException {
        GiftCertificate giftCertificate = certificateDao.findById(id).orElseThrow(EntityNotFoundException::new);
        return certificateConverter.convertToDto(giftCertificate);
    }

    @Override
    @Transactional
    public List<GiftCertificateDto> findByParams(GiftCertificateSearchParamsDto searchParams, int pageNumber, int pageSize) throws InvalidEntityDataException {
        List<TypeOfValidationError> errors = searchParamsValidator.isGiftCertificateSearchParamsDtoValid(searchParams);
        if (!errors.isEmpty()) {
            throw new InvalidEntityDataException(errors, GiftCertificate.class);
        }

        List<String> tagName = searchParams.getTagNames();
        String certificateName = searchParams.getCertificateName();
        String certificateDescription = searchParams.getCertificateDescription();
        String orderByName = searchParams.getOrderByName();
        String orderByCreateDate = searchParams.getOrderByCreateDate();

        return certificateDao.findByParams(tagName, certificateName,
                        certificateDescription, orderByName, orderByCreateDate, pageNumber, pageSize)
                .stream().map(certificateConverter::convertToDto).toList();
    }


    @Override
    @Transactional
    public void updateGiftCertificate(GiftCertificateDto giftCertificateDto) throws EntityNotFoundException, InvalidEntityDataException {
        List<TypeOfValidationError> errors = new ArrayList<>();
        GiftCertificate certificate = certificateDao.findById(giftCertificateDto.getId()).orElseThrow(EntityNotFoundException::new);

        if (giftCertificateDto.getName() != null) {
            if (certificateValidator.isNameValid(giftCertificateDto.getName())) {
                certificate.setName(giftCertificateDto.getName());
            } else {
                errors.add(TypeOfValidationError.INVALID_NAME);
            }
        }
        if (giftCertificateDto.getDescription() != null) {
            if (certificateValidator.isDescriptionValid(giftCertificateDto.getDescription())) {
                certificate.setDescription(giftCertificateDto.getDescription());
            } else {
                errors.add(TypeOfValidationError.INVALID_DESCRIPTION);
            }
        }
        if (giftCertificateDto.getPrice() != null) {
            if (certificateValidator.isPriceValid(giftCertificateDto.getPrice())) {
                certificate.setPrice(giftCertificateDto.getPrice());
            } else {
                errors.add(TypeOfValidationError.INVALID_PRICE);
            }
        }
        if (giftCertificateDto.getDuration() != 0) {
            if (certificateValidator.isDurationValid(giftCertificateDto.getDuration())) {
                certificate.setDuration(giftCertificateDto.getDuration());
            } else {
                errors.add(TypeOfValidationError.INVALID_DURATION);
            }
        }
        if (giftCertificateDto.getTags() != null && !giftCertificateDto.getTags().isEmpty()) {
            certificate.getTags().addAll(giftCertificateDto.getTags().stream().map(tagConverter::convertToEntity).toList());
        }
        if (!errors.isEmpty()) {
            throw new InvalidEntityDataException(errors, GiftCertificate.class);
        }
        certificateDao.update(certificate);
    }

    @Override
    @Transactional
    public void delete(long id) throws EntityNotFoundException {
        GiftCertificate certificate = certificateDao.findById(id).orElseThrow(EntityNotFoundException::new);
        certificateDao.delete(certificate);

    }
}
