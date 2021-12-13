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
        Tag tag = new Tag();
        tag.setId(dto.getId());
        tag.setName(dto.getName());
        return tag;
    }

    /**
     * Convert to dto tag.
     *
     * @param tag the tag
     * @return the tag dto
     */
    public TagDto convertToDto(Tag tag) {
        TagDto dto = new TagDto();
        dto.setId(tag.getId());
        dto.setName(tag.getName());
        return dto;
    }
}
