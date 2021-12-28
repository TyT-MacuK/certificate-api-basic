package com.epam.esm.converter;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import org.springframework.stereotype.Component;

/**
 * The type User converter.
 */
@Component
public class UserConverter {

    /**
     * Convert to user.
     *
     * @param dto the user dto
     * @return the user
     */
    public User convertToEntity(UserDto dto) {
        return User.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }

    /**
     * Convert to user dto.
     *
     * @param user the user
     * @return the user dto
     */
    public UserDto convertToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }
}
