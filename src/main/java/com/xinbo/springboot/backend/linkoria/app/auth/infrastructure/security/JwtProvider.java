package com.xinbo.springboot.backend.linkoria.app.auth.infrastructure.security;

import com.xinbo.springboot.backend.linkoria.app.auth.application.port.out.JwtPort;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtProvider implements JwtPort {

    private static final long ACCESS_TOKEN_MINUTES = 15;

    private final SecretKey secretKey;

    public JwtProvider(@Value("${app.jwt.secret}")SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public String generateAccessToken(UUID userId, String username) {
        long nowMs = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("username",username)
                .setIssuedAt(new Date(nowMs))
                .setExpiration(new Date(nowMs + ACCESS_TOKEN_MINUTES * 1000 * 60))
                .signWith(secretKey)
                .compact();
    }

    @Override
    public UUID extractUserId(String token) {
        return UUID.fromString(parseClaims(token).getSubject());
    }

    @Override
    public String extractUsername(String token) {
        return parseClaims(token).get("username", String.class);
    }

    @Override
    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    //metodo que recibe un JWT, verifica su firma con la clave secreta y devuelve sus claims(payload)
    private Claims parseClaims(String token) {
        Jws<Claims> jwsClaims = Jwts.parserBuilder()
                .setSigningKey(secretKey) // tu SecretKey
                .build()
                .parseClaimsJws(token);   // parsea y verifica la firma

        return jwsClaims.getBody(); // devuelve los claims (payload -> datos de usuario, expiracion, roles, etc)
    }
}
