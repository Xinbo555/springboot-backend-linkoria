package com.xinbo.springboot.backend.linkoria.app.user.infrastructure.persistence.repository;

import com.xinbo.springboot.backend.linkoria.app.user.domain.UserRepository;
import com.xinbo.springboot.backend.linkoria.app.user.infrastructure.persistence.mapper.UserEntityMapper;
import org.springframework.stereotype.Repository;

@Repository
public class JpaUserRepository implements UserRepository {

    private final SpringDataUserRepository springDataUserRepository;
    private final UserEntityMapper mapper;

    public JpaUserRepository(SpringDataUserRepository springDataUserRepository, UserEntityMapper mapper) {
        this.springDataUserRepository = springDataUserRepository;
        this.mapper = mapper;
    }

    @Override
    public User save(User user) {
        UserEntity entity = mapper.toEntity(user);
        UserEntity saved = springDataUserRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return springDataUserRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(Email email) {
        return springDataUserRepository.findByEmail(email.getValue())
                .map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByUsername(Username username) {
        return springDataUserRepository.findByUsername(username.getValue())
                .map(mapper::toDomain);
    }

    @Override
    public List<User> findByUsernameContaining(String partialUsername) {
        return springDataUserRepository.findByUsernameContainingIgnoreCase(partialUsername)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByEmail(Email email) {
        return springDataUserRepository.existsByEmail(email.getValue());
    }

    @Override
    public boolean existsByUsername(Username username) {
        return springDataUserRepository.existsByUsername(username.getValue());
    }

    @Override
    public void deleteById(UUID id) {
        springDataUserRepository.deleteById(id);
    }
}
