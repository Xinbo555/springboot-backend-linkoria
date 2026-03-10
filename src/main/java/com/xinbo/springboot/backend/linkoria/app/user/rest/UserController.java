package com.xinbo.springboot.backend.linkoria.app.user.rest;

import com.xinbo.springboot.backend.linkoria.app.user.application.service.UserService;
import com.xinbo.springboot.backend.linkoria.app.user.domain.User;
import com.xinbo.springboot.backend.linkoria.app.user.rest.dto.CreateUserRequest;
import com.xinbo.springboot.backend.linkoria.app.user.rest.dto.UpdateUserRequest;
import com.xinbo.springboot.backend.linkoria.app.user.rest.dto.UserResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        // Note: password hashing is handled by the auth module before reaching here
        User user = userService.createUser(request.username(), request.email(), request.password());
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.from(user));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserProfile(@PathVariable UUID userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(UserResponse.from(user));
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable UUID userId,
            @Valid @RequestBody UpdateUserRequest request) {
        User user = userService.updateUser(userId, request.username(), request.email(), request.avatarUrl());
        return ResponseEntity.ok(UserResponse.from(user));
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserResponse>> searchUsers(@RequestParam String username) {
        List<UserResponse> users = userService.searchUsers(username)
                .stream()
                .map(UserResponse::from)
                .toList();
        return ResponseEntity.ok(users);
    }
}
