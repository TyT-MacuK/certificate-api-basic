package com.epam.esm.service.impl;

import com.epam.esm.converter.GiftCertificateConverter;
import com.epam.esm.converter.TagConverter;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityAlreadyExistsException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.InvalidEntityDataException;
import com.epam.esm.exception.TypeOfValidationError;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.TagValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {
    private static final Logger logger = LogManager.getLogger();
    private final TagDao tagDao;
    private final TagConverter tagConverter;
    private final TagValidator validator;
    private final GiftCertificateConverter certificateConverter;

    @Autowired
    public TagServiceImpl(TagDao tagDao, TagConverter tagConverter, TagValidator validator,
                          GiftCertificateConverter certificateConverter) {
        this.tagDao = tagDao;
        this.tagConverter = tagConverter;
        this.validator = validator;
        this.certificateConverter = certificateConverter;
    }

    @Override
    @Transactional
    public boolean add(TagDto tagDto) throws InvalidEntityDataException, EntityAlreadyExistsException {
        logger.log(Level.DEBUG, "method add()");
        List<TypeOfValidationError> errorList = validator.validateName(tagDto.getName());
        if (!errorList.isEmpty()) {
            throw new InvalidEntityDataException(errorList, Tag.class);
        }
        Optional<Tag> optionalTag = tagDao.findByName(tagDto.getName());
        if (optionalTag.isPresent()) {
            throw new EntityAlreadyExistsException();
        }
        Tag tag = tagConverter.convertToEntity(tagDto);
        return tagDao.add(tag);
    }

    @Override
    public TagDto findById(Long id) throws EntityNotFoundException {
        logger.log(Level.DEBUG, "method findById()");
        Optional<Tag> tag = tagDao.findById(id);
        return tag.map(tagConverter::convertToDto).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<TagDto> findByPartOfName(String partOfName) {
        logger.log(Level.DEBUG, "method findByPartOfTagName()");
        List<Tag> certificateList = tagDao.findByPartOfName(partOfName);
        return certificateList.stream().map(tagConverter::convertToDto).toList();
    }

    @Override
    @Transactional
    public boolean delete(Long id) throws EntityNotFoundException {
        logger.log(Level.DEBUG, "method delete()");
        boolean isAllCertificatesDetach = tagDao.detachAllCertificates(id);
        if (isAllCertificatesDetach) {
            boolean isDeleted = tagDao.delete(id);
            if (!isDeleted) {
                throw new EntityNotFoundException();
            }
        } else {
            throw new EntityNotFoundException();
        }
        return true;
    }

    @Override
    public List<GiftCertificateDto> findTagCertificates(Long id) throws EntityNotFoundException {
        logger.log(Level.DEBUG, "method findTagCertificates()");
        Optional<Tag> optionalTag = tagDao.findById(id);
        if (optionalTag.isEmpty()) {
            throw new EntityNotFoundException();
        }
        List<GiftCertificate> tagList = tagDao.findTagCertificates(id);
        return tagList.stream().map(certificateConverter::convertToDto).toList();
    }
}
