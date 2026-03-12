package com.xinbo.springboot.backend.linkoria.app.auth.application;

import com.xinbo.springboot.backend.linkoria.app.auth.application.port.in.RefreshTokenUseCase;
import com.xinbo.springboot.backend.linkoria.app.auth.application.port.out.AccessTokenPort;
import com.xinbo.springboot.backend.linkoria.app.auth.application.port.out.RefreshTokenRepository;
import com.xinbo.springboot.backend.linkoria.app.auth.application.port.out.UserServicePort;
import com.xinbo.springboot.backend.linkoria.app.auth.domain.RefreshToken;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.InvalidRefreshTokenException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class RefreshTokenUseCaseImpl implements RefreshTokenUseCase {

    private static final long REFRESH_TOKEN_DAYS = 30;

    private final RefreshTokenRepository refreshTokenRepository;
    private final AccessTokenPort accessTokenPort;
    private final UserServicePort userServicePort;

    public RefreshTokenUseCaseImpl(
            RefreshTokenRepository refreshTokenRepository,
            AccessTokenPort accessTokenPort,
            UserServicePort userServicePort) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.accessTokenPort = accessTokenPort;
        this.userServicePort = userServicePort;
    }

    @Override
    public TokenResult refresh(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new InvalidRefreshTokenException("Refresh token not found"));

        if (!refreshToken.isValid()) {
            throw new InvalidRefreshTokenException("Refresh token is expired or revoked");
        }

        UserServicePort.UserView user = userServicePort.findById(refreshToken.getUserId())
                .orElseThrow(() -> new InvalidRefreshTokenException("User no longer exists"));

        // Token rotation: revoke old, issue new
        refreshTokenRepository.save(refreshToken.revoke());

        RefreshToken newToken = RefreshToken.create(
                user.id(),
                Instant.now().plus(REFRESH_TOKEN_DAYS, ChronoUnit.DAYS)
        );
        RefreshToken savedToken = refreshTokenRepository.save(newToken);

        String newAccessToken = accessTokenPort.generateAccessToken(user.id(), user.username());

        return new TokenResult(newAccessToken, savedToken.getToken());
    }
}