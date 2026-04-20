package com.xinbo.springboot.backend.linkoria.app.user.domain.valueobject;

import com.xinbo.springboot.backend.linkoria.app.shared.exception.user.UserNameFormatException;

import java.util.Objects;

public final class Username {

    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 32;
    private static final String VALID_PATTERN = "^[a-zA-Z0-9._-]+$";

    private final String value;

    private Username(String value) {
        this.value = value;
    }

    public static Username of(String value) {
        validate(value);
        return new Username(value.trim().toLowerCase());
    }

    private static void validate(String value) {
        if (value == null || value.isBlank()) {
            throw new UserNameFormatException("Username cannot be empty");
        }
        String trimmed = value.trim();
        if (trimmed.length() < MIN_LENGTH || trimmed.length() > MAX_LENGTH) {
            throw new UserNameFormatException(
                    "Username must be between " + MIN_LENGTH + " and " + MAX_LENGTH + " characters"
            );
        }
        if (!trimmed.matches(VALID_PATTERN)) {
            throw new UserNameFormatException(
                    "Username can only contain letters, numbers, dots, underscores and hyphens"
            );
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Username)) return false;
        Username username = (Username) o;
        return Objects.equals(value, username.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
