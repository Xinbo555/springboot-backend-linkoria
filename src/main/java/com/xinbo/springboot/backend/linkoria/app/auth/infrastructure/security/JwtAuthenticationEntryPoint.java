package com.xinbo.springboot.backend.linkoria.app.auth.infrastructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper(); // Usado para convertir objetos Java a JSON

    /**
     * Este método se ejecuta automáticamente cuando un usuario no autenticado
     * intenta acceder a un endpoint protegido (requiere autenticación)
     */
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Map<String, Object> body = Map.of(
                "status", 401,
                "error", "Unauthorized",
                "message", "Authentication required",
                "path", request.getRequestURI(),
                "timestamp", Instant.now().toString()
        );

        // Escribimos el cuerpo en la respuesta como JSON
        objectMapper.writeValue(response.getOutputStream(), body);
    }
}
