package com.epam.esm.service.impl;

import com.epam.esm.converter.GiftCertificateConverter;
import com.epam.esm.converter.TagConverter;
import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.InvalidEntityDataException;
import com.epam.esm.exception.InvalidSortOderNameException;
import com.epam.esm.validator.GiftCertificateValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {
    @InjectMocks
    private GiftCertificateServiceImpl service;
    @Mock
    private GiftCertificateDaoImpl certificateDao;
    @Mock
    private GiftCertificateValidator validator;
    @Mock
    private GiftCertificateConverter certificateConverter;
    @Mock
    private TagConverter tagConverter;
    @Mock
    private TagDaoImpl tagDao;

    private static GiftCertificateDto certificateDto;
    private static GiftCertificate certificate;
    private static TagDto tagDto;
    private static Tag tag;

    @BeforeAll
    static void initialize() {
        MockitoAnnotations.openMocks(GiftCertificateServiceImplTest.class);
        initializeGiftCertificateDto();
        initializeGiftCertificate();
        tagDto = new TagDto();
        tag = new Tag();
    }

    @Test
    void addTest() throws InvalidEntityDataException {
        when(validator.isGiftCertificateValid(certificateDto)).thenReturn(new ArrayList<>());
        when(certificateConverter.convertToEntity(certificateDto)).thenReturn(certificate);
        when(certificateDao.add(certificate)).thenReturn(true);
        boolean actual = service.add(certificateDto);
        assertTrue(actual);
    }

    @Test
    void findByIdTest() throws EntityNotFoundException {
        when(certificateDao.findById(1L)).thenReturn(Optional.of(certificate));
        when(certificateConverter.convertToDto(certificate)).thenReturn(certificateDto);
        GiftCertificateDto actual = service.findById(1L);
        assertEquals(certificateDto, actual);
    }

    @Test
    void findByPartOfNameTest() {
        when(certificateDao.findByPartOfName("es")).thenReturn(List.of(certificate));
        when(certificateConverter.convertToDto(certificate)).thenReturn(certificateDto);
        List<GiftCertificateDto> actual = service.findByPartOfName("es");
        assertEquals(List.of(certificateDto), actual);
    }

    @Test
    void findCertificateTagsTest() throws EntityNotFoundException {
        when(certificateDao.findCertificateTags(1L)).thenReturn(List.of(tag));
        when(tagConverter.convertToDto(tag)).thenReturn(tagDto);
        List<TagDto> actual = service.findCertificateTags(1L);
        assertEquals(List.of(tagDto), actual);
    }

    @Test
    void sortCertificateTest() throws InvalidSortOderNameException {
        when(validator.isSortOrderValid("ASC")).thenReturn(true);
        when(certificateDao.sortCertificate("ASC")).thenReturn(List.of(certificate));
        when(certificateConverter.convertToDto(certificate)).thenReturn(certificateDto);
        List<GiftCertificateDto> actual = service.sortCertificate("ASC");
        assertEquals(List.of(certificateDto), actual);
    }

    @Test
    void updateGiftCertificateTest() throws InvalidEntityDataException, EntityNotFoundException {
        when(validator.isGiftCertificateValid(certificateDto)).thenReturn(new ArrayList<>());
        when(certificateDao.findById(1L)).thenReturn(Optional.of(certificate));
        when(certificateDao.update(certificate)).thenReturn(true);
        boolean actual = service.updateGiftCertificate(certificateDto);
        assertTrue(actual);
    }

    @Test
    void attachTest() throws EntityNotFoundException {
        when(certificateDao.findById(1L)).thenReturn(Optional.of(certificate));
        when(tagDao.findById(1L)).thenReturn(Optional.of(tag));
        when(certificateDao.attach(1L, 1L)).thenReturn(true);
        boolean actual = service.attach(1L, 1L);
        assertTrue(actual);
    }

    @Test
    void deleteTest() throws EntityNotFoundException {
        when(certificateDao.detachAllTags(1L)).thenReturn(true);
        when(certificateDao.delete(1L)).thenReturn(true);
        boolean actual = service.delete(1L);
        assertTrue(actual);
    }

    private static void initializeGiftCertificateDto() {
        certificateDto = new GiftCertificateDto.Builder()
                .setId(1)
                .setName("test")
                .setDescription("test test")
                .setPrice(new BigDecimal(10))
                .setDuration(5)
                .setLastUpdateDate(LocalDateTime.of(2021, 12, 1, 12, 0, 0))
                .setCreateDate(LocalDateTime.of(2021, 12, 1, 12, 0, 0))
                .build();
    }

    private static void initializeGiftCertificate() {
        certificate = new GiftCertificate.Builder()
                .setId(1)
                .setName("test")
                .setDescription("test test")
                .setPrice(new BigDecimal(10))
                .setDuration(5)
                .setLastUpdateDate(LocalDateTime.of(2021, 12, 1, 12, 0, 0))
                .setCreateDate(LocalDateTime.of(2021, 12, 1, 12, 0, 0))
                .build();
    }
}
