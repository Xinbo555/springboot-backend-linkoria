package com.xinbo.springboot.backend.linkoria.app.auth.application;

import com.xinbo.springboot.backend.linkoria.app.auth.application.port.in.RegisterUseCase;
import com.xinbo.springboot.backend.linkoria.app.auth.application.port.out.JwtPort;
import com.xinbo.springboot.backend.linkoria.app.auth.application.port.out.RefreshTokenRepository;
import com.xinbo.springboot.backend.linkoria.app.auth.application.port.out.UserServicePort;
import com.xinbo.springboot.backend.linkoria.app.auth.domain.AuthService;
import com.xinbo.springboot.backend.linkoria.app.auth.domain.RefreshToken;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.EmailAlreadyTakenException;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.UsernameAlreadyTakenException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class RegisterUseCaseImpl implements RegisterUseCase {

    private static final long REFRESH_TOKEN_DAYS = 30;

    private final AuthService authService;
    private final UserServicePort userServicePort;
    private final JwtPort jwtPort;
    private final RefreshTokenRepository refreshTokenRepository;

    public RegisterUseCaseImpl(
            AuthService authService,
            UserServicePort userServicePort,
            JwtPort jwtPort,
            RefreshTokenRepository refreshTokenRepository) {
        this.authService = authService;
        this.userServicePort = userServicePort;
        this.jwtPort = jwtPort;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public AuthResult register(RegisterCommand command) {
        if (userServicePort.existsByEmail(command.email())) {
            throw new EmailAlreadyTakenException("Email is already in use");
        }
        if (userServicePort.existsByUsername(command.username())) {
            throw new UsernameAlreadyTakenException("Username is already taken");
        }

        authService.validatePasswordStrength(command.password());
        String passwordHash = authService.hashPassword(command.password());

        UserServicePort.UserView user = userServicePort.createUser(
                new UserServicePort.CreateUserRequest(command.username(), command.email(), passwordHash)
        );

        String accessToken = jwtPort.generateAccessToken(user.id(), user.username());

        RefreshToken refreshToken = RefreshToken.create(
                user.id(),
                Instant.now().plus(REFRESH_TOKEN_DAYS, ChronoUnit.DAYS)
        );
        RefreshToken savedToken = refreshTokenRepository.save(refreshToken);

        return new AuthResult(accessToken, savedToken.getToken(), user.id(), user.username());
    }
}