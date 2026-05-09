package com.xinbo.springboot.backend.linkoria.app.auth.application;

import com.xinbo.springboot.backend.linkoria.app.auth.application.port.in.LoginUseCase;
import com.xinbo.springboot.backend.linkoria.app.auth.application.port.out.AccessTokenPort;
import com.xinbo.springboot.backend.linkoria.app.auth.domain.RefreshTokenRepository;
import com.xinbo.springboot.backend.linkoria.app.auth.application.port.out.UserServicePort;
import com.xinbo.springboot.backend.linkoria.app.auth.domain.AuthService;
import com.xinbo.springboot.backend.linkoria.app.auth.domain.RefreshToken;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.auth.InvalidCredentialsException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

//mala praxis de anotacion
@Service
public class LoginUseCaseImpl implements LoginUseCase {

    private static final long REFRESH_TOKEN_DAYS = 30;

    private final AuthService authService;
    private final UserServicePort userServicePort;
    private final AccessTokenPort accessTokenPort;
    private final RefreshTokenRepository refreshTokenRepository;

    public LoginUseCaseImpl(
            AuthService authService,
            UserServicePort userServicePort,
            AccessTokenPort accessTokenPort,
            RefreshTokenRepository refreshTokenRepository) {
        this.authService = authService;
        this.userServicePort = userServicePort;
        this.accessTokenPort = accessTokenPort;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public AuthResult login(LoginCommand command) {
        UserServicePort.UserView user = userServicePort.findByEmail(command.email())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        if (!authService.verifyPassword(command.password(), user.passwordHash())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        String accessToken = accessTokenPort.generateAccessToken(user.id(), user.username());

        RefreshToken refreshToken = RefreshToken.create(
                user.id(),
                Instant.now().plus(REFRESH_TOKEN_DAYS, ChronoUnit.DAYS)
        );
        RefreshToken savedToken = refreshTokenRepository.save(refreshToken);

        return new AuthResult(accessToken, savedToken.getToken(), user.id(), user.username(), savedToken.getExpiresAt());
    }
}
