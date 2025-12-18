package com.hardware.hardware_structure.core.utility;

import com.hardware.hardware_structure.core.configuration.AppConfigurationProperties;
import com.hardware.hardware_structure.model.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtUtils {
    private final AppConfigurationProperties appConfigurationProperties;

    public String extractEmail(final String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Long extractUserId(final String token) {
        Object userIdClaim = extractAllClaims(token).get("user_id");
        if (userIdClaim == null) {
            return null;
        }
        if (userIdClaim instanceof Number) {
            return ((Number) userIdClaim).longValue();
        }
        if (userIdClaim instanceof String) {
            return Long.parseLong((String) userIdClaim);
        }
        throw new IllegalArgumentException("Invalid user_id type in token");
    }

    public Date extractExpiration(final String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(final String token, final Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        String secretKey = appConfigurationProperties.getJwt().getSecretKey();
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Boolean isTokenExpired(final String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateAccessToken(final UserEntity user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("jti", UUID.randomUUID().toString());
        claims.put("user_id", user.getId());
        claims.put("role", user.getRole().name());
        return createToken(claims, user.getEmail(), TimeUnit.MINUTES.toMillis(15));
    }

    public String generateRefreshToken(final UserEntity user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("jti", UUID.randomUUID().toString());
        return createToken(claims, user.getEmail(), TimeUnit.DAYS.toMillis(30));
    }

    private String createToken(final Map<String, Object> claims, final String subject, final Long expiration) {
        String secretKey = appConfigurationProperties.getJwt().getSecretKey();
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }

    public Boolean validateToken(final String token, final UserDetails userDetails) {
        final String username = extractEmail(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
