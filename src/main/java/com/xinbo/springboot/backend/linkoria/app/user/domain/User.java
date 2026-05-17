package com.xinbo.springboot.backend.linkoria.app.user.domain;

import com.xinbo.springboot.backend.linkoria.app.user.domain.valueobject.Email;
import com.xinbo.springboot.backend.linkoria.app.user.domain.valueobject.Username;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class User {

    private final UUID id;
    private Username username;
    private Email email;
    private String bio;
    private final String passwordHash;
    private String avatarUrl;
    private boolean isActive;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    //constructor a la hora de crear un nuevo usuario
    private User(UUID id, Username username, Email email, String passwordHash, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.createdAt = createdAt;
        this.updatedAt = createdAt;
        this.isActive = true;
    }

    //constructor para reconstuir desde la persistencia
    private User(UUID id, Username username, Email email, String passwordHash, String avatarUrl, String bio, boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.avatarUrl = avatarUrl;
        this.bio = bio;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    //static factory methods
    public static User create(Username username, Email email, String passwordHash) {
        Objects.requireNonNull(username, "Username cannot be null");
        Objects.requireNonNull(email, "Email cannot be null");
        Objects.requireNonNull(passwordHash, "Password hash cannot be null");

        return new User(UUID.randomUUID(), username, email, passwordHash, LocalDateTime.now());
    }

    public static User reconstitute(UUID id, Username username, Email email, String passwordHash,
                                    String avatarUrl, String bio, boolean isActive,
                                    LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new User(id, username, email, passwordHash, avatarUrl, bio, isActive, createdAt, updatedAt);
    }

    //metodos para indicar una modificacion en la cuenta
    public void updateUsername(Username newUsername) {
        Objects.requireNonNull(newUsername, "Username cannot be null");
        this.username = newUsername;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateEmail(Email newEmail) {
        Objects.requireNonNull(newEmail, "Email cannot be null");
        this.email = newEmail;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateBio(String bio) {
        this.bio = bio;
        this.updatedAt = LocalDateTime.now();
    }

    public void deactivate() {
        this.isActive = false;
        this.updatedAt = LocalDateTime.now();
    }

    public void activate() {
        this.isActive = true;
        this.updatedAt = LocalDateTime.now();
    }

    // Getters
    public UUID getId() { return id; }
    public Username getUsername() { return username; }
    public Email getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public String getAvatarUrl() { return avatarUrl; }
    public boolean isActive() { return isActive; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public String getBio() { return bio; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", username=" + username + ", email=" + email + "}";
    }
}