package com.epam.esm.service.impl;

import com.epam.esm.converter.GiftCertificateConverter;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagDao tagDao;
    private final TagConverter tagConverter;
    private final TagValidator validator;
    private final GiftCertificateConverter certificateConverter;

    @Override
    @Transactional
    public void add(TagDto tagDto) throws InvalidEntityDataException, EntityAlreadyExistsException {
        List<TypeOfValidationError> errorList = validator.validateName(tagDto.getName());
        if (!errorList.isEmpty()) {
            throw new InvalidEntityDataException(errorList, Tag.class);
        }
        Optional<Tag> optionalTag = tagDao.findByName(tagDto.getName());
        if (optionalTag.isPresent()) {
            throw new EntityAlreadyExistsException();
        }
        Tag tag = tagConverter.convertToEntity(tagDto);
        tagDao.add(tag);
    }

    @Override
    public TagDto findById(Long id) throws EntityNotFoundException {
        Optional<Tag> tag = tagDao.findById(id);
        return tag.map(tagConverter::convertToDto).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    @Transactional
    public void delete(Long id) throws EntityNotFoundException {
        Tag tag = tagDao.findById(id).orElseThrow(EntityNotFoundException::new);
        tagDao.delete(tag);
    }

    @Override
    public List<TagDto> findAll() {
        return tagDao.findAll().stream().map(tagConverter::convertToDto).toList();
    }
}
