package com.epam.esm.filter;

import com.epam.esm.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The Custom token filter. The filter looks for a token in every request
 */
@Component
@RequiredArgsConstructor
public class CustomTokenFilter extends OncePerRequestFilter {
    public static final String AUTHORIZATION = "Authorization";
    public static final String PREFIX_BEARER = "Bearer ";

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String jwtToken = parseJwt(request);
        if (jwtToken != null && jwtUtil.validateToken(jwtToken)) {
            String userName = jwtUtil.getUserNameFromJwtToken(jwtToken);
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuthentication = request.getHeader(AUTHORIZATION);
        String result = null;
        if (headerAuthentication != null &&!headerAuthentication.isBlank()
                && headerAuthentication.startsWith(PREFIX_BEARER)) {
            result = headerAuthentication.substring(7);
        }
        return result;
    }
}