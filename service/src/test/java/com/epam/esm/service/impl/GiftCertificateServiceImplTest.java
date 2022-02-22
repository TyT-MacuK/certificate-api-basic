package com.epam.esm.service.impl;

import com.epam.esm.converter.GiftCertificateConverter;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateSearchParamsDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.EntityNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {
    @InjectMocks
    private GiftCertificateServiceImpl service;
    @Mock
    private GiftCertificateDao certificateDao;
    @Mock
    private GiftCertificateConverter certificateConverter;

    private static GiftCertificateDto certificateDto;
    private static GiftCertificate certificate;

    @BeforeAll
    static void initialize() {
        MockitoAnnotations.openMocks(GiftCertificateServiceImplTest.class);
        certificateDto = GiftCertificateDto.builder().build();
        certificate = GiftCertificate.builder().build();
    }

    @Test
    void addTest() {
        when(certificateConverter.convertToEntity(certificateDto)).thenReturn(certificate);
        service.add(certificateDto);
        verify(certificateDao).save(certificate);
    }

    @Test
    void findAllTest() {
        when(certificateDao.findAll(any(PageRequest.class))).thenReturn(new PageImpl<>(List.of(certificate)));
        when(certificateConverter.convertToDto(certificate)).thenReturn(certificateDto);
        List<GiftCertificateDto> actual = service.findAll(1, 1);
        assertEquals(List.of(certificateDto), actual);
    }

    @Test
    void findByIdTest() throws EntityNotFoundException {
        when(certificateDao.findById(any(Long.class))).thenReturn(Optional.of(certificate));
        when(certificateConverter.convertToDto(certificate)).thenReturn(certificateDto);
        GiftCertificateDto actual = service.findById(1L);
        assertEquals(certificateDto, actual);
    }

    @Test
    void findByParamsTest() {
        when(certificateDao.findAll(any(Specification.class), any(PageRequest.class)))
                .thenReturn(new PageImpl<>(List.of(certificate)));
        when(certificateConverter.convertToDto(certificate)).thenReturn(certificateDto);
        List<GiftCertificateDto> actual =
                service.findByParams(GiftCertificateSearchParamsDto.builder().build(), 1, 1);
        assertEquals(List.of(certificateDto), actual);
    }

    @Test
    void updateGiftCertificateTest() throws EntityNotFoundException {
        when(certificateDao.findById(any(Long.class))).thenReturn(Optional.of(certificate));
        service.updateGiftCertificate(certificateDto);
        verify(certificateDao).save(certificate);
    }

    @Test
    void deleteTest() throws EntityNotFoundException {
        when(certificateDao.findById(any(Long.class))).thenReturn(Optional.of(certificate));
        service.delete(1L);
        verify(certificateDao).delete(certificate);
    }
}
