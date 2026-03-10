package com.xinbo.springboot.backend.linkoria.app.user.domain.valueobject;

import java.util.Objects;

public final class Email {

    private static final String VALID_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    private final String value;

    private Email(String value) {
        this.value = value;
    }

    public static Email of(String value) {
        validate(value);
        return new Email(value.trim().toLowerCase());
    }

    private static void validate(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (!value.trim().matches(VALID_PATTERN)) {
            throw new IllegalArgumentException("Invalid email format: " + value);
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Email email)) return false;
        return Objects.equals(value, email.value);
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
