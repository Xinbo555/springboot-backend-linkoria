package com.xinbo.springboot.backend.linkoria.app.auth.infrastructure.security;

import com.xinbo.springboot.backend.linkoria.app.auth.application.port.out.AccessTokenPort;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

//Esta clase es un filtro de Spring Security que extrae el JWT de la petición, lo valida y, si es válido,
//carga el usuario autenticado en el contexto de seguridad para que esté disponible en toda la solicitud.
public class BearerTokenAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final AccessTokenPort accessTokenPort;

    public BearerTokenAuthenticationFilter(AccessTokenPort accessTokenPort) {
        this.accessTokenPort = accessTokenPort;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = extractToken(request);

        if (StringUtils.hasText(token) && accessTokenPort.validateToken(token)) {
            UUID userId = accessTokenPort.extractUserId(token);
            String username = accessTokenPort.extractUsername(token);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            new AuthenticatedUser(userId, username),
                            null,
                            List.of()
                    );

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(header) && header.startsWith(BEARER_PREFIX)) {
            return header.substring(BEARER_PREFIX.length());
        }
        return null;
    }
}
