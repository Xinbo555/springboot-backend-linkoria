package com.xinbo.springboot.backend.linkoria.app.auth.infrastructure.persistence;

import com.xinbo.springboot.backend.linkoria.app.auth.application.port.out.RefreshTokenRepository;
import com.xinbo.springboot.backend.linkoria.app.auth.domain.RefreshToken;
import com.xinbo.springboot.backend.linkoria.app.auth.infrastructure.persistence.entity.RefreshTokenEntity;
import com.xinbo.springboot.backend.linkoria.app.auth.infrastructure.persistence.mapper.RefreshTokenMapper;
import com.xinbo.springboot.backend.linkoria.app.auth.infrastructure.persistence.repository.RefreshTokenJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Component
public class RefreshTokenRepositoryAdapter implements RefreshTokenRepository {

    private final RefreshTokenJpaRepository jpaRepository;

    public RefreshTokenRepositoryAdapter(RefreshTokenJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    @Transactional
    public RefreshToken save(RefreshToken refreshToken) {
        RefreshTokenEntity entity = RefreshTokenMapper.toEntity(refreshToken);
        RefreshTokenEntity saved = jpaRepository.save(entity);
        return RefreshTokenMapper.toDomain(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RefreshToken> findByToken(String token) {
        return jpaRepository.findByToken(token)
                .map(RefreshTokenMapper::toDomain);
    }

    // TODO: llamar a revokeAllByUserId cuando el usuario cambie su contraseña o sea baneado
    // TODO: implementar POST /auth/logout-all para cerrar sesión en todos los dispositivos

    // TODO: crear RefreshTokenCleanupJob con @Scheduled para ejecutar deleteExpiredTokens cada noche

    @Transactional
    @Override
    public void revokeAllByUserId(UUID userId) {
        jpaRepository.revokeAllByUserId(userId);
    }

    @Override
    public void deleteExpiredTokens() {
        jpaRepository.deleteExpiredTokens(Instant.now());
    }
}
