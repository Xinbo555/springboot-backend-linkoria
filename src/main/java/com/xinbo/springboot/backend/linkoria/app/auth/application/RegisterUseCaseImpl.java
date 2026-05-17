package com.xinbo.springboot.backend.linkoria.app.auth.application;

import com.xinbo.springboot.backend.linkoria.app.auth.application.port.in.RegisterUseCase;
import com.xinbo.springboot.backend.linkoria.app.auth.application.port.out.AccessTokenPort;
import com.xinbo.springboot.backend.linkoria.app.auth.domain.RefreshTokenRepository;
import com.xinbo.springboot.backend.linkoria.app.auth.application.port.out.UserServicePort;
import com.xinbo.springboot.backend.linkoria.app.auth.domain.AuthService;
import com.xinbo.springboot.backend.linkoria.app.auth.domain.RefreshToken;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.auth.EmailAlreadyTakenException;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.auth.UsernameAlreadyTakenException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class RegisterUseCaseImpl implements RegisterUseCase {

    private static final long REFRESH_TOKEN_DAYS = 30;

    private final AuthService authService;
    private final UserServicePort userServicePort;
    private final AccessTokenPort accessTokenPort;
    private final RefreshTokenRepository refreshTokenRepository;

    public RegisterUseCaseImpl(
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
    public AuthResult register(RegisterCommand command) {
        if (userServicePort.existsByEmail(command.email())) {
            throw new EmailAlreadyTakenException("Email is already in use");
        }
        if (userServicePort.existsByUsername(command.username())) {
            throw new UsernameAlreadyTakenException("Username is already taken: "+ command.username());
        }

        authService.validatePasswordStrength(command.password());
        String passwordHash = authService.hashPassword(command.password());

        UserServicePort.UserView user = userServicePort.createUser(
                new UserServicePort.CreateUserRequest(command.username(), command.email(), passwordHash)
        );

        String accessToken = accessTokenPort.generateAccessToken(user.id(), user.username());

        RefreshToken refreshToken = RefreshToken.create(
                user.id(),
                Instant.now().plus(REFRESH_TOKEN_DAYS, ChronoUnit.DAYS)
        );
        RefreshToken savedToken = refreshTokenRepository.save(refreshToken);

        return new AuthResult(accessToken, savedToken.getToken(), user.id(), user.username(), savedToken.getExpiresAt());
    }
}