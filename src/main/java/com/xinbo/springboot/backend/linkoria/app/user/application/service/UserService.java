package com.xinbo.springboot.backend.linkoria.app.user.application.service;

import com.xinbo.springboot.backend.linkoria.app.user.application.usecase.*;
import com.xinbo.springboot.backend.linkoria.app.user.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final GetUserProfileUseCase getUserProfileUseCase;
    private final SearchUsersUseCase searchUsersUseCase;
    private final FindUserUseCase findUserUseCase;

    public UserService(CreateUserUseCase createUserUseCase, UpdateUserUseCase updateUserUseCase, GetUserProfileUseCase getUserProfileUseCase, SearchUsersUseCase searchUsersUseCase, FindUserUseCase findUserUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.getUserProfileUseCase = getUserProfileUseCase;
        this.searchUsersUseCase = searchUsersUseCase;
        this.findUserUseCase = findUserUseCase;
    }

    public User updateUser(UUID userId, String newUsername, String newEmail, String newAvatarUrl) {
        return updateUserUseCase.execute(new UpdateUserUseCase.Input(userId,newUsername,newEmail,newAvatarUrl));
    }

    public User getUserById(UUID uuid) {
        return getUserProfileUseCase.execute(uuid);
    }

    public List<User> searchUsers(String partialUsername){
        return searchUsersUseCase.execute(partialUsername);
    }

    public User createUser(String username, String email, String passwordHash){
        return createUserUseCase.execute(new CreateUserUseCase.Input(username,email,passwordHash));
    }

    public Optional<User> findByEmail(String email) {
        return findUserUseCase.findByEmail(email);
    }

    public Optional<User> findById(UUID id) {
        return findUserUseCase.findById(id);
    }

    public boolean existsByEmail(String email) {
        return findUserUseCase.existsByEmail(email);
    }

    public boolean existsByUsername(String username) {
        return findUserUseCase.existsByUsername(username);
    }

    public boolean existsById(UUID id){return findUserUseCase.existsById(id);}
}
