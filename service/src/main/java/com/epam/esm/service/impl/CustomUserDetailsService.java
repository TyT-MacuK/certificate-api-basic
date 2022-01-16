package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.security.CustomUserDetails;
import com.epam.esm.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private static final String USER_NOT_FOUND_PARAM = "user_not_found";
    private final UserDao userDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByName(username).orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_PARAM));
        return CustomUserDetails.convertUserToCustomUserDetails(user);
    }
}
