package com.epam.esm.service.impl;

import com.epam.esm.converter.TagConverter;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityAlreadyExistsException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagDao tagDao;
    private final TagConverter tagConverter;
    private final UserDao userDao;

    @Override
    @Transactional
    public void add(TagDto tagDto) throws EntityAlreadyExistsException {
        Optional<Tag> optionalTag = tagDao.findByName(tagDto.getName());
        if (optionalTag.isPresent()) {
            throw new EntityAlreadyExistsException();
        }
        Tag tag = tagConverter.convertToEntity(tagDto);
        tagDao.save(tag);
    }

    @Override
    public TagDto findById(long id) throws EntityNotFoundException {
        Optional<Tag> tag = tagDao.findById(id);
        return tag.map(tagConverter::convertToDto).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<TagDto> findAll(int page, int pageSize) {
        return tagDao.findAll(PageRequest.of(page - 1, pageSize)).stream().map(tagConverter::convertToDto).toList();
    }

    @Override
    public TagDto findMostWidelyUsedTag(Long userId) throws EntityNotFoundException {
        userDao.findById(userId).orElseThrow(EntityNotFoundException::new);
        Tag tag = tagDao.findMostWidelyUsedTag(userId).orElseThrow(EntityNotFoundException::new);
        return tagConverter.convertToDto(tag);
    }

    @Override
    @Transactional
    public void delete(long id) throws EntityNotFoundException {
        Tag tag = tagDao.findById(id).orElseThrow(EntityNotFoundException::new);
        tagDao.delete(tag);
    }
}
