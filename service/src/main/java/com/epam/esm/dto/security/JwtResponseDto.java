package com.epam.esm.dto.security;

import lombok.Getter;
import lombok.Setter;

public class JwtResponseDto {
    @Getter
    private static final String TYPE = "Bearer";
    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    private String token;

    public JwtResponseDto(long id, String token) {
        this.id = id;
        this.token = token;
    }
}
