package com.epam.esm.service.impl;

import com.epam.esm.converter.TagConverter;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityAlreadyExistsException;
import com.epam.esm.exception.EntityNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {
    @InjectMocks
    private TagServiceImpl service;
    @Mock
    TagDao tagDao;
    @Mock
    private TagConverter tagConverter;
    @Mock
    private UserDao userDao;

    private static TagDto tagDto;
    private static Tag tag;
    private static User user;

    @BeforeAll
    static void initialize() {
        MockitoAnnotations.openMocks(TagServiceImplTest.class);
        tagDto = TagDto.builder().name("name").build();
        tag = Tag.builder().name("name").build();;
        user = new User();
    }

    @Test
    void addTest() throws EntityAlreadyExistsException {
        when(tagDao.findByName(any(String.class))).thenReturn(Optional.empty());
        when(tagConverter.convertToEntity(tagDto)).thenReturn(tag);
        service.add(tagDto);
        verify(tagDao).save(tag);
    }

    @Test
    void findByIdTest() throws EntityNotFoundException {
        when(tagDao.findById(any(Long.class))).thenReturn(Optional.of(tag));
        when(tagConverter.convertToDto(tag)).thenReturn(tagDto);
        TagDto actual = service.findById(1L);
        assertEquals(tagDto, actual);
    }

    @Test
    void findMostWidelyUsedTagTest() throws EntityNotFoundException {
        when(userDao.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(tagDao.findMostWidelyUsedTag(any(Long.class))).thenReturn(Optional.of(tag));
        when(tagConverter.convertToDto(any(Tag.class))).thenReturn(tagDto);
        TagDto actual = service.findMostWidelyUsedTag(1L);
        assertEquals(tagDto, actual);
    }

    @Test
    void deleteTest() throws EntityNotFoundException {
        when(tagDao.findById(any(Long.class))).thenReturn(Optional.of(tag));
        service.delete(1L);
        verify(tagDao).delete(tag);
    }
}