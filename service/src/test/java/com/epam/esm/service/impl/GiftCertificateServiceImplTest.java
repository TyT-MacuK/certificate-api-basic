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
import static org.mockito.Mockito.verify;
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
        tagDto = TagDto.builder().id(1).name("test").build();
        tag = Tag.builder().name("test").build();
        tag.setId(1);
        initializeGiftCertificateDto();
        initializeGiftCertificate();
    }

    @Test
    void addTest() throws InvalidEntityDataException {
        when(validator.isGiftCertificateValid(certificateDto)).thenReturn(new ArrayList<>());
        when(certificateConverter.convertToEntity(certificateDto)).thenReturn(certificate);
        service.add(certificateDto);
        verify(certificateDao).add(certificateConverter.convertToEntity(certificateDto));
    }

    @Test
    void findAllTest() {
        when(certificateDao.findAll(1, 1)).thenReturn(List.of(certificate));
        List<GiftCertificateDto> actual = service.findAll(1, 1);
        assertEquals(List.of(certificateDto), actual);
    }

    @Test
    void findByIdTest() throws EntityNotFoundException {
        when(certificateDao.findById(1L)).thenReturn(Optional.of(certificate));
        when(certificateConverter.convertToDto(certificate)).thenReturn(certificateDto);
        GiftCertificateDto actual = service.findById(1L);
        assertEquals(certificateDto, actual);
    }

    @Test
    void updateGiftCertificateTest() throws InvalidEntityDataException, EntityNotFoundException {
        when(validator.isNameValid(certificateDto.getName())).thenReturn(true);
        when(validator.isDescriptionValid(certificateDto.getDescription())).thenReturn(true);
        when(validator.isPriceValid(certificateDto.getPrice())).thenReturn(true);
        when(validator.isDurationValid(certificateDto.getDuration())).thenReturn(true);
        when(certificateDao.findById(1L)).thenReturn(Optional.of(certificate));
        service.updateGiftCertificate(certificateDto);
        verify(certificateDao).update(certificate);
    }

    @Test
    void deleteTest() throws EntityNotFoundException {
        when(certificateDao.findById(1L)).thenReturn(Optional.of(certificate));
        service.delete(1L);
        verify(certificateDao).delete(certificate);
    }

    private static void initializeGiftCertificateDto() {
        certificateDto = GiftCertificateDto.builder()
                .id(1)
                .name("test")
                .description("test test")
                .price(new BigDecimal(10))
                .duration(5)
                .lastUpdateDay(LocalDateTime.of(2021, 12, 1, 12, 0, 0))
                .createDay(LocalDateTime.of(2021, 12, 1, 12, 0, 0))
                .build();
    }

    private static void initializeGiftCertificate() {
        certificate = GiftCertificate.builder()
                .name("test")
                .description("test test")
                .price(new BigDecimal(10))
                .duration(5)
                .lastUpdateDate(LocalDateTime.of(2021, 12, 1, 12, 0, 0))
                .createDate(LocalDateTime.of(2021, 12, 1, 12, 0, 0))
                .tags(List.of(tag))
                .build();
        certificate.setId(1);
    }
}
