package com.epam.esm.converter;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * The type Tag converter.
 */
@Component
@RequiredArgsConstructor
public class TagConverter {
    private final GiftCertificateConverter certificateConverter;

    /**
     * Convert to tag.
     *
     * @param dto the dto
     * @return the tag
     */
    public Tag convertToEntity(TagDto dto) {
        return Tag.builder().id(dto.getId()).name(dto.getName())
                .giftCertificates(dto.getGiftCertificates().stream().map(certificateConverter::convertToEntity).toList())
                .build();
    }

    /**
     * Convert to dto tag.
     *
     * @param tag the tag
     * @return the tag dto
     */
    public TagDto convertToDto(Tag tag) {
        return TagDto.builder().id(tag.getId()).name(tag.getName())
                .giftCertificates(tag.getGiftCertificates().stream().map(certificateConverter::convertToDto).toList())
                .build();
    }
}
