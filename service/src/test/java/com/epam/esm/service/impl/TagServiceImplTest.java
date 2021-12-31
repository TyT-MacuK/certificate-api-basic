package com.epam.esm.service.impl;

import com.epam.esm.converter.GiftCertificateConverter;
import com.epam.esm.converter.TagConverter;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.validator.TagValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {
    @InjectMocks
    private TagServiceImpl service;
    @Mock
    TagDaoImpl tagDao;
    @Mock
    private TagValidator validator;
    @Mock
    private TagConverter tagConverter;
    @Mock
    private GiftCertificateConverter certificateConverter;

    private static TagDto tagDto;
    private static Tag tag;
    private static GiftCertificateDto certificateDto;
    private static GiftCertificate certificate;

    @BeforeAll
    static void initialize() {
        MockitoAnnotations.openMocks(TagServiceImplTest.class);
        tagDto = TagDto.builder().id(1).name("name").build();

//        tag = new Tag();
//        tag.setId(1);
//        tag.setName("name");
//
//        certificateDto = new GiftCertificateDto();
//        certificate = new GiftCertificate();
    }

//    @Test
//    void addTest() throws InvalidEntityDataException, EntityAlreadyExistsException {
//        when(validator.validateName(tagDto.getName())).thenReturn(new ArrayList<>());
//        when(tagDao.findByName(tagDto.getName())).thenReturn(Optional.empty());
//        when(tagConverter.convertToEntity(tagDto)).thenReturn(tag);
//        when(tagDao.add(tag)).thenReturn(true);
//        boolean actual = service.add(tagDto);
//        assertTrue(actual);
//    }

    @Test
    void findByIdTest() throws EntityNotFoundException {
        when(tagDao.findById(tagDto.getId())).thenReturn(Optional.of(tag));
        when(tagConverter.convertToDto(tag)).thenReturn(tagDto);
        TagDto actual = service.findById(1L);
        assertEquals(tagDto, actual);
    }

//    @Test
//    void findByPartOfNameTest() {
//        when(tagDao.findByPartOfName("es")).thenReturn(List.of(tag));
//        when(tagConverter.convertToDto(tag)).thenReturn(tagDto);
//        List<TagDto> actual = service.findByPartOfName("es");
//        assertEquals(List.of(tagDto), actual);
//    }

//    @Test
//    void deleteTest() throws EntityNotFoundException {
//        when(tagDao.detachAllCertificates(1L)).thenReturn(true);
//        when(tagDao.delete(1L)).thenReturn(true);
//        boolean actual = service.delete(1L);
//        assertTrue(actual);
//    }

//    @Test
//    void findTagCertificatesTest() throws EntityNotFoundException {
//        when(tagDao.findById(1L)).thenReturn(Optional.of(tag));
//        when(tagDao.findTagCertificates(1L)).thenReturn(List.of(certificate));
//        when(certificateConverter.convertToDto(certificate)).thenReturn(certificateDto);
//        List<GiftCertificateDto> actual = service.findTagCertificates(1L);
//        assertEquals(List.of(certificateDto), actual);
//    }
}