package com.epam.esm.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SignUpDto {
    @NotNull(message = "{name_null}")
    @Size(min = 2, max = 45, message = "{name_length_error}")
    private String name;

    @NotNull(message = "{email_null}")
    @Email(message = "{email_error}")
    private String email;

    @NotNull(message = "{password_null}")
    @Size(min = 5, max = 60, message = "{password_length_error}")
    private String password;
}
