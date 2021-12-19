package com.epam.esm.converter;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.stereotype.Component;

/**
 * The type Gift certificate converter.
 */
@Component
public class GiftCertificateConverter {
    /**
     * Convert to gift certificate.
     *
     * @param dto the dto
     * @return the gift certificate
     */
    public GiftCertificate convertToEntity(GiftCertificateDto dto) {
        return GiftCertificate.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .duration(dto.getDuration())
                .createDay(dto.getCreateDay())
                .lastUpdateDay(dto.getLastUpdateDay())
                .build();
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
                .createDay(certificate.getCreateDay())
                .lastUpdateDay(certificate.getLastUpdateDay())
                .build();
    }
}
