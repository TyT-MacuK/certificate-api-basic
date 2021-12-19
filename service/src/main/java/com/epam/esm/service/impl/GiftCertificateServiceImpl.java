package com.epam.esm.service.impl;

import com.epam.esm.converter.GiftCertificateConverter;
import com.epam.esm.converter.TagConverter;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateSearchParamsDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.AttachException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.InvalidEntityDataException;
import com.epam.esm.exception.TypeOfValidationError;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.GiftCertificateValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateDao certificateDao;
    private final GiftCertificateConverter certificateConverter;
    private final GiftCertificateValidator validator;
    private final TagDao tagDao;
    private final TagConverter tagConverter;

    @Override
    @Transactional
    public boolean add(GiftCertificateDto giftCertificateDto) throws InvalidEntityDataException {
        List<TypeOfValidationError> errors = validator.isGiftCertificateValid(giftCertificateDto);
        if (!errors.isEmpty()) {
            throw new InvalidEntityDataException(errors, GiftCertificate.class);
        }
        GiftCertificate certificate = certificateConverter.convertToEntity(giftCertificateDto);
        LocalDateTime createDay = LocalDateTime.now();
        certificate.setCreateDay(createDay);
        certificate.setLastUpdateDay(createDay);
        return certificateDao.add(certificate);
    }

    @Override
    public GiftCertificateDto findById(Long id) throws EntityNotFoundException {
        GiftCertificate giftCertificate = certificateDao.findById(id).orElseThrow(EntityNotFoundException::new);
        return certificateConverter.convertToDto(giftCertificate);
    }

    @Override
    @Transactional
    public Map<GiftCertificateDto, List<TagDto>> findByParams(GiftCertificateSearchParamsDto searchParams) throws InvalidEntityDataException {
        List<TypeOfValidationError> errors = validator.isGiftCertificateSearchParamsDtoValid(searchParams);
        if (!errors.isEmpty()) {
            throw new InvalidEntityDataException(errors, GiftCertificate.class);
        }

        String tagName = searchParams.getTagName();
        String certificateName = searchParams.getCertificateName();
        String certificateDescription = searchParams.getCertificateDescription();
        String orderByName = searchParams.getOrderByName();
        String orderByCreateDate = searchParams.getOrderByCreateDate();

        List<GiftCertificateDto> allCertificate = certificateDao.findByParams(tagName, certificateName,
                        certificateDescription, orderByName, orderByCreateDate)
                .stream().map(certificateConverter::convertToDto).toList();

        Map<GiftCertificateDto, List<TagDto>> result = new HashMap<>();
        for(GiftCertificateDto certificate : allCertificate) {
            List <Tag> tags = certificateDao.findCertificateTags(certificate.getId());
            result.put(certificate, tags.stream().map(tagConverter::convertToDto).toList());
        }
        return result;
    }

    @Override
    public List<TagDto> findCertificateTags(Long giftCertificateId) {
        List<Tag> tags = certificateDao.findCertificateTags(giftCertificateId);
        return tags.stream().map(tagConverter::convertToDto).toList();
    }

    @Override
    @Transactional
    public boolean updateGiftCertificate(GiftCertificateDto giftCertificateDto) throws EntityNotFoundException, InvalidEntityDataException {
        List<TypeOfValidationError> errors = new ArrayList<>();
        GiftCertificate certificate = certificateDao.findById(giftCertificateDto.getId()).orElseThrow(EntityNotFoundException::new);

        if (giftCertificateDto.getName() != null) {
            if (validator.isNameValid(giftCertificateDto.getName())) {
                certificate.setName(giftCertificateDto.getName());
            } else {
                errors.add(TypeOfValidationError.INVALID_NAME);
            }
        }
        if (giftCertificateDto.getDescription() != null) {
            if (validator.isDescriptionValid(giftCertificateDto.getDescription())) {
                certificate.setDescription(giftCertificateDto.getDescription());
            } else {
                errors.add(TypeOfValidationError.INVALID_DESCRIPTION);
            }
        }
        if (giftCertificateDto.getPrice() != null) {
            if (validator.isPriceValid(giftCertificateDto.getPrice())) {
                certificate.setPrice(giftCertificateDto.getPrice());
            } else {
                errors.add(TypeOfValidationError.INVALID_PRICE);
            }
        }
        if (giftCertificateDto.getDuration() != 0) {
            if (validator.isDurationValid(giftCertificateDto.getDuration())) {
                certificate.setDuration(giftCertificateDto.getDuration());
            } else {
                errors.add(TypeOfValidationError.INVALID_DURATION);
            }
        }
        if (!errors.isEmpty()) {
            throw new InvalidEntityDataException(errors, GiftCertificate.class);
        }
        LocalDateTime lastUpdateDate = LocalDateTime.now();
        certificate.setLastUpdateDay(lastUpdateDate);
        return certificateDao.update(certificate);
    }

    @Override
    @Transactional
    public boolean attach(Long giftCertificateId, Long tagId) throws AttachException {
        Optional<GiftCertificate> optionalCertificate = certificateDao.findById(giftCertificateId);
        Optional<Tag> optionalTag = tagDao.findById(tagId);
        List<Class<?>> notFoundEntityList = new ArrayList<>();
        if (optionalCertificate.isEmpty() && optionalTag.isEmpty()) {
            notFoundEntityList.add(GiftCertificate.class);
            notFoundEntityList.add(Tag.class);
        } else if (optionalCertificate.isEmpty()) {
            notFoundEntityList.add(GiftCertificate.class);
        } else if (optionalTag.isEmpty()) {
            notFoundEntityList.add(Tag.class);
        }
        if (!notFoundEntityList.isEmpty()) {
            throw new AttachException(notFoundEntityList);
        }
        return certificateDao.attach(giftCertificateId, tagId);
    }


    @Override
    @Transactional
    public boolean delete(Long id) throws EntityNotFoundException {
        boolean isAllTagDetach = certificateDao.detachAllTags(id);
        if (isAllTagDetach) {
            boolean isDeleted = certificateDao.delete(id);
            if (!isDeleted) {
                throw new EntityNotFoundException();
            }
        } else {
            throw new EntityNotFoundException();
        }
        return true;
    }
}
