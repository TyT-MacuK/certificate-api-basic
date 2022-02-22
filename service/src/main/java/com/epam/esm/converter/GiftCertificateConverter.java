package com.epam.esm.converter;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * The type Gift certificate converter.
 */
@Component
@RequiredArgsConstructor
public class GiftCertificateConverter {
    private final TagConverter tagConverter;

    /**
     * Convert to gift certificate.
     *
     * @param dto the dto
     * @return the gift certificate
     */
    public GiftCertificate convertToEntity(GiftCertificateDto dto) {
         GiftCertificate certificate = GiftCertificate.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .duration(dto.getDuration())
                .createDate(dto.getCreateDay())
                .lastUpdateDate(dto.getLastUpdateDay())
                .build();
        List<Tag> tags = dto.getTags() != null ? dto.getTags().stream().map(tagConverter::convertToEntity).toList()
                : Collections.emptyList();
        certificate.setTags(tags);
        certificate.setId(dto.getId());
        return certificate;
    }

    /**
     * Convert to dto gift certificate.
     *
     * @param certificate the certificate
     * @return the gift certificate dto
     */
    public GiftCertificateDto convertToDto(GiftCertificate certificate) {
        return GiftCertificateDto.builder()
                .id(certificate.getId())
                .name(certificate.getName())
                .description(certificate.getDescription())
                .price(certificate.getPrice())
                .duration(certificate.getDuration())
                .createDay(certificate.getCreateDate())
                .lastUpdateDay(certificate.getLastUpdateDate())
                .tags(certificate.getTags().stream().map(tagConverter::convertToDto).toList())
                .build();
    }
}
