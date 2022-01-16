package com.epam.esm.util;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Log4j2
public class JwtUtil {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration_milliseconds}")
    private int jwtExpirationMs;

    public String generateJwtToken(String login) {
        return Jwts.builder()
                .setSubject(login)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.warn("Token expired. Message: {}", expEx.getMessage());
        } catch (UnsupportedJwtException unsEx) {
            log.warn("Unsupported jwt. Message: {}", unsEx.getMessage());
        } catch (MalformedJwtException mjEx) {
            log.warn("Malformed jwt. Message: {}", mjEx.getMessage());
        } catch (SignatureException sEx) {
            log.warn("Invalid signature. Message: {}", sEx.getMessage());
        } catch (Exception e) {
            log.warn("Invalid token. Message: {}", e.getMessage());
        }
        return false;
    }
}
