package com.epam.esm.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TagDto extends AbstractDto {
    private long id;
    private String name;

}
