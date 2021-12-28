package com.epam.esm.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.epam.esm.exception.LocalizationExceptionColumn.NAME_LENGTH_ERROR;

@Data
@Builder
@Relation(collectionRelation = "content", itemRelation = "user")
public class UserDto extends RepresentationModel<UserDto> {
    private long id;

    @NotNull
    @Size(min = 2, max = 45, message = NAME_LENGTH_ERROR)
    private String name;
}
