package com.epam.esm.service;

import com.epam.esm.dto.LoginDto;
import com.epam.esm.dto.SignUpDto;
import com.epam.esm.dto.security.JwtResponseDto;
import com.epam.esm.exception.EntityAlreadyExistsException;

/**
 * The interface User authentication.
 */
public interface UserAuthentication {

    /**
     * Sign up.
     *
     * @param signUpDto the sign up dto
     * @throws EntityAlreadyExistsException the entity already exists exception
     */
    void signUp(SignUpDto signUpDto) throws EntityAlreadyExistsException;

    /**
     * Login in application.
     *
     * @param loginDto the login dto
     * @return the jwt response dto
     */
    JwtResponseDto login(LoginDto loginDto);
}
