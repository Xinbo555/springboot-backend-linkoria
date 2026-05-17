package com.xinbo.springboot.backend.linkoria.app.user.rest;

import com.xinbo.springboot.backend.linkoria.app.user.application.service.UserService;
import com.xinbo.springboot.backend.linkoria.app.user.domain.User;
import com.xinbo.springboot.backend.linkoria.app.user.rest.dto.UpdateUserRequest;
import com.xinbo.springboot.backend.linkoria.app.user.rest.dto.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Users", description = "Gestión de perfiles de usuario — consulta, actualización y búsqueda")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Obtener perfil de usuario",
            description = "Devuelve el perfil público de un usuario dado su UUID. Requiere accessToken válido."
    )
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserProfile(@PathVariable UUID userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(UserResponse.from(user));
    }

    @Operation(
            summary = "Actualizar perfil de usuario",
            description = "Actualiza los campos username, email, avatarUrl y bio del usuario. Solo se aplican los campos enviados en el body."
    )
    @PatchMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable UUID userId,
            @Valid @RequestBody UpdateUserRequest request) {
        User user = userService.updateUser(userId, request.username(), request.email(), request.avatarUrl(), request.bio());
        return ResponseEntity.ok(UserResponse.from(user));
    }

    @Operation(
            summary = "Buscar usuarios por nombre",
            description = "Devuelve una lista de usuarios cuyo username contenga el texto indicado. La búsqueda no distingue mayúsculas de minúsculas."
    )
    @GetMapping("/search")
    public ResponseEntity<List<UserResponse>> searchUsers(@RequestParam String username) {
        List<UserResponse> users = userService.searchUsers(username)
                .stream()
                .map(UserResponse::from)
                .toList();
        return ResponseEntity.ok(users);
    }
}
