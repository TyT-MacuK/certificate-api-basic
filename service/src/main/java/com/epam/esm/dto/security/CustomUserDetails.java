package com.epam.esm.dto.security;

import com.epam.esm.entity.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
@NoArgsConstructor
public class CustomUserDetails implements UserDetails {
    private long id;
    private String login;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> grantedAuthorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public static CustomUserDetails convertUserToCustomUserDetails(User user) {
        CustomUserDetails userDetails = new CustomUserDetails();
        userDetails.id = user.getId();
        userDetails.login = user.getName();
        userDetails.email = user.getEmail();
        userDetails.password = user.getPassword();
        userDetails.grantedAuthorities = Collections.singletonList(
                new SimpleGrantedAuthority(user.getRole().getRole().name()));
        return userDetails;
    }
}
