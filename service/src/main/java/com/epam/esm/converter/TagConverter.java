package com.epam.esm.converter;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Component;

/**
 * The type Tag converter.
 */
@Component
public class TagConverter {

    /**
     * Convert to tag.
     *
     * @param dto the dto
     * @return the tag
     */
    public Tag convertToEntity(TagDto dto) {
        Tag tag = Tag.builder().name(dto.getName()).build();
        tag.setId(dto.getId());
        return tag;
    }

    /**
     * Convert to dto tag.
     *
     * @param tag the tag
     * @return the tag dto
     */
    public TagDto convertToDto(Tag tag) {
        return TagDto.builder().id(tag.getId()).name(tag.getName()).build();
    }
}
