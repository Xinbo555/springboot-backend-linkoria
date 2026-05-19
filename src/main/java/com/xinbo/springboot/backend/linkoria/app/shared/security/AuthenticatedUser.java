package com.xinbo.springboot.backend.linkoria.app.shared.security;

import java.security.Principal;
import java.util.UUID;

public class AuthenticatedUser implements Principal {

    private final UUID id;
    private final String username;

    public AuthenticatedUser(UUID id, String username) {
        this.id = id;
        this.username = username;
    }

    public UUID getId() { return id; }
    public String getUsername() { return username; }

    @Override
    public String toString() {
        return "AuthenticatedUser{id=" + id + ", username='" + username + "'}";
    }

    @Override
    public String getName() {
        return username;
    }
}