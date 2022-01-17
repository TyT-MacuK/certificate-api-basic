package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Relation(collectionRelation = "content", itemRelation = "tag")
public class TagDto extends RepresentationModel<TagDto> {
    private long id;

    @NotNull(message = "{name_null}")
    @Size(min = 2, max = 45, message = "{name_length_error}")
    private String name;

    @JsonIgnore
    private List<GiftCertificateDto> giftCertificates;
}
