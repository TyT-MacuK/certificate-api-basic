package com.epam.esm.service.impl;

import com.epam.esm.converter.GiftCertificateConverter;
import com.epam.esm.converter.TagConverter;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.GiftCertificateSearchSpecificationBuilder;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateSearchParamsDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.service.GiftCertificateService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateDao certificateDao;
    private final GiftCertificateConverter certificateConverter;
    private final TagConverter tagConverter;

    @Override
    @Transactional
    public void add(GiftCertificateDto giftCertificateDto) {
        GiftCertificate certificate = certificateConverter.convertToEntity(giftCertificateDto);
        certificateDao.save(certificate);
    }

    @Override
    public List<GiftCertificateDto> findAll(int pageNumber, int pageSize) {
        return certificateDao.findAll(PageRequest.of(pageNumber - 1, pageSize))
                .map(certificateConverter::convertToDto).toList();
    }

    @Override
    public GiftCertificateDto findById(long id) throws EntityNotFoundException {
        GiftCertificate giftCertificate = certificateDao.findById(id).orElseThrow(EntityNotFoundException::new);
        return certificateConverter.convertToDto(giftCertificate);
    }

    @Override
    @Transactional
    public List<GiftCertificateDto> findByParams(GiftCertificateSearchParamsDto searchParams, int pageNumber, int pageSize) {
        String certificateName = searchParams.getCertificateName();
        String certificateDescription = searchParams.getCertificateDescription();
        List<String> tagNames = searchParams.getTagNames();
        String orderByName = searchParams.getOrderByName();
        String orderByCreateDate = searchParams.getOrderByCreateDate();

        Specification<GiftCertificate> specification = new GiftCertificateSearchSpecificationBuilder()
                .certificateName(certificateName)
                .certificateDescription(certificateDescription)
                .tagNames(tagNames)
                .orderByName(orderByName)
                .orderByCreateDate(orderByCreateDate)
                .build();

        return certificateDao.findAll(specification, PageRequest.of(pageNumber - 1, pageSize))
                .map(certificateConverter::convertToDto).toList();
    }


    @Override
    @Transactional
    public void updateGiftCertificate(GiftCertificateDto giftCertificateDto) throws EntityNotFoundException {
        GiftCertificate certificate = certificateDao.findById(giftCertificateDto.getId()).orElseThrow(EntityNotFoundException::new);
        if (giftCertificateDto.getTags() != null && !giftCertificateDto.getTags().isEmpty()) {
            certificate.getTags().addAll(giftCertificateDto.getTags().stream().map(tagConverter::convertToEntity).toList());
        }
        certificateDao.save(certificate);
    }

    @Override
    @Transactional
    public void delete(long id) throws EntityNotFoundException {
        GiftCertificate certificate = certificateDao.findById(id).orElseThrow(EntityNotFoundException::new);
        certificateDao.delete(certificate);
    }
}
