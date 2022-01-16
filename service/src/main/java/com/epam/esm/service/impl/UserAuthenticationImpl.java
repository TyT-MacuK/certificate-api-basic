package com.epam.esm.service.impl;

import com.epam.esm.dao.RoleDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.LoginDto;
import com.epam.esm.dto.SignUpDto;
import com.epam.esm.dto.security.CustomUserDetails;
import com.epam.esm.dto.security.JwtResponseDto;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityAlreadyExistsException;
import com.epam.esm.service.UserAuthentication;
import com.epam.esm.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserAuthenticationImpl implements UserAuthentication {
    public final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDao userDao;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleDao roleDao;

    @Override
    @Transactional
    public JwtResponseDto login(LoginDto loginDto) {
        String jwtToken = jwtUtil.generateJwtToken(loginDto.getName());
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getName(), loginDto.getPassword()));
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new JwtResponseDto(userDetails.getId(), jwtToken);
    }

    @Override
    @Transactional
    public void signUp(SignUpDto signUpDto) throws EntityAlreadyExistsException {
        if (userDao.existByEmail(signUpDto.getEmail())) {
            throw new EntityAlreadyExistsException();
        }
        if (userDao.existByName(signUpDto.getName())) {
            throw new EntityAlreadyExistsException();
        }
        User user = User.builder()
                .name(signUpDto.getName())
                .email(signUpDto.getEmail())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .role(roleDao.findByName(User.UserRole.ROLE_USER.name()))
                .build();
        userDao.save(user);
    }
}
