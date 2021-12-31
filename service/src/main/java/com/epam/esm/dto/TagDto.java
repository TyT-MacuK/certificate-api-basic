package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.List;

import static com.epam.esm.exception.LocalizationExceptionColumn.NAME_NULL;
import static com.epam.esm.exception.LocalizationExceptionColumn.NAME_LENGTH_ERROR;

@Data
@Builder
@Relation(collectionRelation = "content", itemRelation = "tag")
public class TagDto extends RepresentationModel<TagDto> {
    private long id;

    @NotNull(message = NAME_NULL)
    @Size(min = 2, max = 45, message = NAME_LENGTH_ERROR)
    private String name;

    @JsonIgnore
    private List<GiftCertificateDto> giftCertificates;
}
