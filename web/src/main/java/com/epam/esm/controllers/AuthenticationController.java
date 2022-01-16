package com.epam.esm.controllers;

import com.epam.esm.dto.LoginDto;
import com.epam.esm.dto.SignUpDto;
import com.epam.esm.dto.security.JwtResponseDto;
import com.epam.esm.exception.EntityAlreadyExistsException;
import com.epam.esm.service.UserAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserAuthentication userAuthentication;

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@Valid @RequestBody LoginDto loginDto) {
        return new ResponseEntity<>(userAuthentication.login(loginDto), HttpStatus.OK);
    }

    @PostMapping("/sign_up")
    public ResponseEntity<Void> signUp(@Valid @RequestBody SignUpDto signUpDto) throws EntityAlreadyExistsException {
        userAuthentication.signUp(signUpDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}