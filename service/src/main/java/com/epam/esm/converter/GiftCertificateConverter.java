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
        return new GiftCertificate.Builder()
                .setId(dto.getId())
                .setName(dto.getName())
                .setDescription(dto.getDescription())
                .setPrice(dto.getPrice())
                .setDuration(dto.getDuration())
                .setCreateDate(dto.getCreateDay())
                .setLastUpdateDate(dto.getLastUpdateDay())
                .build();
    }

    /**
     * Convert to dto gift certificate.
     *
     * @param certificate the certificate
     * @return the gift certificate dto
     */
    public GiftCertificateDto convertToDto(GiftCertificate certificate) {
        return new GiftCertificateDto.Builder()
                .setId(certificate.getId())
                .setName(certificate.getName())
                .setDescription(certificate.getDescription())
                .setPrice(certificate.getPrice())
                .setDuration(certificate.getDuration())
                .setCreateDate(certificate.getCreateDay())
                .setLastUpdateDate(certificate.getLastUpdateDay())
                .build();
    }
}
