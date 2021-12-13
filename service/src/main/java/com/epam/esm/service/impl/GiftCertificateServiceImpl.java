package com.epam.esm.service.impl;

import com.epam.esm.converter.GiftCertificateConverter;
import com.epam.esm.converter.TagConverter;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.InvalidEntityDataException;
import com.epam.esm.exception.InvalidSortOderNameException;
import com.epam.esm.exception.TypeOfValidationError;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.GiftCertificateValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private static final Logger logger = LogManager.getLogger();
    private final GiftCertificateDao certificateDao;
    private final GiftCertificateConverter certificateConverter;
    private final GiftCertificateValidator validator;
    private final TagDao tagDao;
    private final TagConverter tagConverter;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao certificateDao, GiftCertificateConverter certificateConverter,
                                      GiftCertificateValidator validator, TagDao tagDao, TagConverter tagConverter
    ) {
        this.certificateDao = certificateDao;
        this.certificateConverter = certificateConverter;
        this.validator = validator;
        this.tagDao = tagDao;
        this.tagConverter = tagConverter;
    }

    @Override
    @Transactional
    public boolean add(GiftCertificateDto giftCertificateDto) throws InvalidEntityDataException {
        logger.log(Level.DEBUG, "method add()");
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
        logger.log(Level.DEBUG, "method findById()");
        GiftCertificate giftCertificate = certificateDao.findById(id).orElseThrow(EntityNotFoundException::new);
        return certificateConverter.convertToDto(giftCertificate);
    }

    @Override
    public List<GiftCertificateDto> findByPartOfName(String partOfName) {
        logger.log(Level.DEBUG, "method findByPartOfTagName()");
        List<GiftCertificate> certificateList = certificateDao.findByPartOfName(partOfName);
        return certificateList.stream().map(certificateConverter::convertToDto).toList();
    }

    @Override
    public List<TagDto> findCertificateTags(Long giftCertificateId) throws EntityNotFoundException {
        logger.log(Level.DEBUG, "method findCertificateTags()");
        List<Tag> tags = certificateDao.findCertificateTags(giftCertificateId);
        if (tags.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return tags.stream().map(tagConverter::convertToDto).toList();
    }

    @Override
    public List<GiftCertificateDto> sortCertificate(String sortOrder) throws InvalidSortOderNameException {
        logger.log(Level.DEBUG, "method sortCertificate()");
        if (!validator.isSortOrderValid(sortOrder)) {
            throw new InvalidSortOderNameException();
        }
        List<GiftCertificate> certificateList = certificateDao.sortCertificate(sortOrder.toUpperCase());
        return certificateList.stream().map(certificateConverter::convertToDto).toList();
    }

    @Override
    @Transactional
    public boolean updateGiftCertificate(GiftCertificateDto giftCertificateDto) throws EntityNotFoundException, InvalidEntityDataException {
        logger.log(Level.DEBUG, "method updateGiftCertificate()");
        List<TypeOfValidationError> errors = validator.isGiftCertificateValid(giftCertificateDto);
        if (!errors.isEmpty()) {
            throw new InvalidEntityDataException(errors, GiftCertificate.class);
        }
        Optional<GiftCertificate> optionalGiftCertificate = certificateDao.findById(giftCertificateDto.getId());
        if (optionalGiftCertificate.isEmpty()) {
            throw new EntityNotFoundException();
        }
        GiftCertificate certificate = optionalGiftCertificate.get();
        if (giftCertificateDto.getName() != null) {
            certificate.setName(giftCertificateDto.getName());
        }
        if (giftCertificateDto.getDescription() != null) {
            certificate.setDescription(giftCertificateDto.getDescription());
        }
        if (giftCertificateDto.getPrice() != null) {
            certificate.setPrice(giftCertificateDto.getPrice());
        }
        LocalDateTime lastUpdateDate = LocalDateTime.now();
        certificate.setLastUpdateDay(lastUpdateDate);
        return certificateDao.update(certificate);
    }

    @Override
    @Transactional
    public boolean attach(Long giftCertificateId, Long tagId) throws EntityNotFoundException {
        logger.log(Level.DEBUG, "method attachTag()");
        Optional<GiftCertificate> optionalCertificate = certificateDao.findById(giftCertificateId);
        Optional<Tag> optionalTag = tagDao.findById(tagId);
        if (optionalCertificate.isEmpty() || optionalTag.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return certificateDao.attach(giftCertificateId, tagId);
    }


    @Override
    @Transactional
    public boolean delete(Long id) throws EntityNotFoundException {
        logger.log(Level.DEBUG, "method delete()");
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
