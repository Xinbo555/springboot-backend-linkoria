package com.xinbo.springboot.backend.linkoria.app.user.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(

        @Size(min = 3, max = 32, message = "Username must be between 3 and 32 characters")
        String username,

        @Email(message = "Invalid email format")
        String email,

        String avatarUrl
) {}
