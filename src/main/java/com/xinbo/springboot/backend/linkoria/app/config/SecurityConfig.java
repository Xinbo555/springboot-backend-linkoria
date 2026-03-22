package com.xinbo.springboot.backend.linkoria.app.config;

import com.xinbo.springboot.backend.linkoria.app.auth.application.port.out.AccessTokenPort;
import com.xinbo.springboot.backend.linkoria.app.auth.infrastructure.security.JwtAuthenticationEntryPoint;
import com.xinbo.springboot.backend.linkoria.app.auth.infrastructure.security.BearerTokenAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint entryPoint;
    private final AccessTokenPort accessTokenPort;

    public SecurityConfig(JwtAuthenticationEntryPoint entryPoint, AccessTokenPort accessTokenPort) {
        this.entryPoint = entryPoint;
        this.accessTokenPort = accessTokenPort;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // Desactiva CSRF porque la API es stateless y usa JWT, no cookies de sesión
                .csrf(AbstractHttpConfigurer::disable)
                // No se crean sesiones en servidor — cada petición se autentica por JWT
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Si la petición no está autenticada, delega en JwtAuthenticationEntryPoint (devuelve 401 en JSON)
                .exceptionHandling(ex -> ex.authenticationEntryPoint(entryPoint))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/login", "/api/v1/auth/register", "/api/v1/auth/refresh").permitAll()
                        .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/error").permitAll()
                        .anyRequest().authenticated()
                )
                // Registra JwtFilter antes del filtro de autenticación estándar de Spring
                // para que el token JWT sea procesado primero y se pueble el SecurityContext
                .addFilterBefore(new BearerTokenAuthenticationFilter(accessTokenPort), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
