package com.xinbo.springboot.backend.linkoria.app.auth.application;

import com.xinbo.springboot.backend.linkoria.app.auth.application.port.in.LogoutUseCase;
import com.xinbo.springboot.backend.linkoria.app.auth.domain.RefreshTokenRepository;
import com.xinbo.springboot.backend.linkoria.app.auth.domain.RefreshToken;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.TokenNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LogoutUseCaseImpl implements LogoutUseCase {
    private final RefreshTokenRepository refreshTokenRepository;

    public LogoutUseCaseImpl(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public void logout(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new TokenNotFoundException("Refresh token not found"));

        RefreshToken revoked = refreshToken.revoke();
        refreshTokenRepository.save(revoked);
    }
}
