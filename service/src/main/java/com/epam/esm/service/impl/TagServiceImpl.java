package com.epam.esm.service.impl;

import com.epam.esm.converter.TagConverter;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
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

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {
    private static final Logger logger = LogManager.getLogger();
    private final TagDao tagDao;
    private final TagConverter converter;
    private final TagValidator validator;

    @Autowired
    public TagServiceImpl(TagDao tagDao, TagConverter converter, TagValidator validator) {
        this.tagDao = tagDao;
        this.converter = converter;
        this.validator = validator;
    }

    @Override
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
        Tag tag = converter.convertToEntity(tagDto);
        return tagDao.add(tag);
    }

    @Override
    public TagDto findById(Long id) throws EntityNotFoundException {
        logger.log(Level.DEBUG, "method findById()");
        Optional<Tag> tag = tagDao.findById(id);
        return tag.map(converter::convertToDto).orElseThrow(() -> new EntityNotFoundException(id));
    }

    @Override
    public boolean delete(Long id) throws EntityNotFoundException {
        logger.log(Level.DEBUG, "method delete()");
        boolean isDeleted = tagDao.delete(id);
        if (!isDeleted) {
            throw new EntityNotFoundException(id);
        }
        return true;
    }
}
