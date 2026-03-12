package com.xinbo.springboot.backend.linkoria.app.auth.application;

import com.xinbo.springboot.backend.linkoria.app.auth.application.port.in.RefreshTokenUseCase;
import com.xinbo.springboot.backend.linkoria.app.auth.application.port.out.JwtPort;
import com.xinbo.springboot.backend.linkoria.app.auth.application.port.out.RefreshTokenRepository;
import com.xinbo.springboot.backend.linkoria.app.auth.application.port.out.UserServicePort;
import com.xinbo.springboot.backend.linkoria.app.auth.domain.RefreshToken;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.InvalidRefreshTokenException;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.TokenNotFoundException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class RefreshTokenUseCaseImpl implements RefreshTokenUseCase {

    private static final long REFRESH_TOKEN_DAYS = 30;

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtPort jwtPort;
    private final UserServicePort userServicePort;

    public RefreshTokenUseCaseImpl(JwtPort jwtPort, RefreshTokenRepository refreshTokenRepository, UserServicePort userServicePort) {
        this.jwtPort = jwtPort;
        this.refreshTokenRepository = refreshTokenRepository;
        this.userServicePort = userServicePort;
    }

    @Override
    public TokenResult refresh(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new TokenNotFoundException("Refresh token not found"));

        if (!refreshToken.isValid()) {
            throw new InvalidRefreshTokenException("Refresh token is expired or revoked");
        }

        UserServicePort.UserView user = userServicePort.findById(refreshToken.getUserId())
                .orElseThrow(() -> new InvalidRefreshTokenException("User no loger exists"));

        // Rotamos: revocamos el viejo y creamos uno nuevo registrandolo en la ddbb
        RefreshToken revoked = refreshToken.revoke();
        refreshTokenRepository.save(revoked);

        RefreshToken newToken = RefreshToken.create(
                user.id(),
                Instant.now().plus(REFRESH_TOKEN_DAYS, ChronoUnit.DAYS)
        );
        RefreshToken savedToken = refreshTokenRepository.save(newToken);

        //Creamos el nuevo AccessToken
        String newAccessToken = jwtPort.generateAccessToken(user.id(),user.username());

        return new TokenResult(newAccessToken, savedToken.getToken());
    }
}
