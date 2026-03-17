package com.xinbo.springboot.backend.linkoria.app.auth.domain;

import com.xinbo.springboot.backend.linkoria.app.shared.exception.auth.WeakPasswordException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String hashPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public boolean verifyPassword(String rawPassword,String hashedPassword) {
        return passwordEncoder.matches(rawPassword,hashedPassword);
    }

    public void validatePasswordStrength(String rawPassword) {
        if (rawPassword == null || rawPassword.length() < 8) {
            throw new WeakPasswordException("Password must be at least 8 characters long");
        }
    }
}
