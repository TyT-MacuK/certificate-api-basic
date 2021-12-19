package com.epam.esm.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Tag extends AbstractEntity {
    private long id;
    private String name;
}
