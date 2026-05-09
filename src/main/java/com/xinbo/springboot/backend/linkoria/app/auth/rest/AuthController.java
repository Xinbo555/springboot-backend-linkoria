package com.xinbo.springboot.backend.linkoria.app.auth.rest;

import com.xinbo.springboot.backend.linkoria.app.auth.application.port.in.LoginUseCase;
import com.xinbo.springboot.backend.linkoria.app.auth.application.port.in.LogoutUseCase;
import com.xinbo.springboot.backend.linkoria.app.auth.application.port.in.RefreshTokenUseCase;
import com.xinbo.springboot.backend.linkoria.app.auth.application.port.in.RegisterUseCase;
import com.xinbo.springboot.backend.linkoria.app.auth.rest.dto.AuthRequest;
import com.xinbo.springboot.backend.linkoria.app.auth.rest.dto.AuthResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth", description = "Authentication endpoints — register, login, token refresh and logout")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final LoginUseCase loginUseCase;
    private final RegisterUseCase registerUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;
    private final LogoutUseCase logoutUseCase;

    public AuthController(
            LoginUseCase loginUseCase,
            RegisterUseCase registerUseCase,
            RefreshTokenUseCase refreshTokenUseCase,
            LogoutUseCase logoutUseCase) {
        this.loginUseCase = loginUseCase;
        this.registerUseCase = registerUseCase;
        this.refreshTokenUseCase = refreshTokenUseCase;
        this.logoutUseCase = logoutUseCase;
    }

    @Operation(
            summary = "Iniciar sesión",
            description = "Autentica al usuario con email y contraseña. Devuelve un accessToken de corta duración (15 min) y un refreshToken de larga duración (30 días)."
    )
    @PostMapping("/login")
    public ResponseEntity<AuthResponse.AuthTokenResponse> login(@RequestBody AuthRequest.LoginRequest request) {
        LoginUseCase.AuthResult result = loginUseCase.login(
                new LoginUseCase.LoginCommand(request.email(), request.password())
        );
        return ResponseEntity.ok(
                AuthResponse.AuthTokenResponse.of(result.accessToken(), result.refreshToken(), result.userId(), result.username(), result.refreshTokenExpiresAt())
        );
    }

    @Operation(
            summary = "Registrar usuario",
            description = "Crea una nueva cuenta de usuario y devuelve los tokens inmediatamente. La contraseña es hasheada por el módulo auth antes de persistirse."
    )
    @PostMapping("/register")
    public ResponseEntity<AuthResponse.AuthTokenResponse> register(@RequestBody AuthRequest.RegisterRequest request) {
        RegisterUseCase.AuthResult result = registerUseCase.register(
                new RegisterUseCase.RegisterCommand(request.username(), request.email(), request.password())
        );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(AuthResponse.AuthTokenResponse.of(result.accessToken(), result.refreshToken(), result.userId(), result.username(), result.refreshTokenExpiresAt()));
    }

    @Operation(
            summary = "Renovar access token",
            description = "Intercambia un refreshToken válido por un nuevo accessToken y un refreshToken rotado. El refreshToken anterior queda invalidado inmediatamente."
    )
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse.TokenRefreshResponse> refresh(@RequestBody AuthRequest.RefreshRequest request) {
        RefreshTokenUseCase.TokenResult result = refreshTokenUseCase.refresh(request.refreshToken());
        return ResponseEntity.ok(
                AuthResponse.TokenRefreshResponse.of(result.accessToken(), result.refreshToken(), result.refreshTokenExpiresAt())
        );
    }

    @Operation(
            summary = "Cerrar sesión",
            description = "Revoca el refreshToken en base de datos. El accessToken sigue siendo válido hasta que expire — el cliente debe descartarlo."
    )
    @PostMapping("/logout")
    public ResponseEntity<AuthResponse.MessageResponse> logout(@RequestBody AuthRequest.LogoutRequest request) {
        logoutUseCase.logout(request.refreshToken());
        return ResponseEntity.ok(new AuthResponse.MessageResponse("Logged out successfully"));
    }
}
