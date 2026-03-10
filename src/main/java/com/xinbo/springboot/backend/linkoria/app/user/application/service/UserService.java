package com.xinbo.springboot.backend.linkoria.app.user.application.service;

import com.xinbo.springboot.backend.linkoria.app.shared.exception.ResourceNotFoundException;
import com.xinbo.springboot.backend.linkoria.app.user.application.usecase.CreateUserUseCase;
import com.xinbo.springboot.backend.linkoria.app.user.application.usecase.GetUserProfileUseCase;
import com.xinbo.springboot.backend.linkoria.app.user.application.usecase.SearchUsersUseCase;
import com.xinbo.springboot.backend.linkoria.app.user.application.usecase.UpdateUserUseCase;
import com.xinbo.springboot.backend.linkoria.app.user.domain.User;
import com.xinbo.springboot.backend.linkoria.app.user.domain.UserRepository;
import com.xinbo.springboot.backend.linkoria.app.user.domain.valueobject.Email;
import com.xinbo.springboot.backend.linkoria.app.user.domain.valueobject.Username;
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
    private final UserRepository userRepository;

    public UserService(CreateUserUseCase createUserUseCase, UpdateUserUseCase updateUserUseCase, GetUserProfileUseCase getUserProfileUseCase, SearchUsersUseCase searchUsersUseCase, UserRepository userRepository) {
        this.createUserUseCase = createUserUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.getUserProfileUseCase = getUserProfileUseCase;
        this.searchUsersUseCase = searchUsersUseCase;
        this.userRepository = userRepository;
    }

    public User createUser(String username, String email, String passwordHash) {
        return createUserUseCase.execute(new CreateUserUseCase.Input(username,email,passwordHash));
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

    //Methods para consulta directa sin pasar por el flujo de los usecases para otros modulos (auth, friendship, etc.)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(Email.of(email));
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(Username.of(username));
    }
}
