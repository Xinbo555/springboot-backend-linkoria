package com.xinbo.springboot.backend.linkoria.app.shared;

import com.xinbo.springboot.backend.linkoria.app.shared.exception.auth.InvalidCredentialsException;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.auth.InvalidRefreshTokenException;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.auth.TokenNotFoundException;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.friendship.FriendshipAlreadyExistsException;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.friendship.FriendshipNotFoundException;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.friendship.FriendshipStatusException;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.friendship.ReceiverIdNotFoundException;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.auth.EmailAlreadyTakenException;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.ResourceNotFoundException;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.auth.UsernameAlreadyTakenException;
import com.xinbo.springboot.backend.linkoria.app.shared.exception.auth.WeakPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //  Auth

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<?> handleInvalidCredentials(InvalidCredentialsException ex) {
        return build(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(InvalidRefreshTokenException.class)
    public ResponseEntity<?> handleInvalidRefreshToken(InvalidRefreshTokenException ex) {
        return build(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<?> handleTokenNotFound(TokenNotFoundException ex) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(EmailAlreadyTakenException.class)
    public ResponseEntity<?> handleEmailAlreadyTaken(EmailAlreadyTakenException ex) {
        return build(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(UsernameAlreadyTakenException.class)
    public ResponseEntity<?> handleUsernameAlreadyTaken(UsernameAlreadyTakenException ex) {
        return build(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(WeakPasswordException.class)
    public ResponseEntity<?> handleWeakPassword(WeakPasswordException ex) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    //  User

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFound(ResourceNotFoundException ex) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    //  Friendship

    @ExceptionHandler(FriendshipNotFoundException.class)
    public ResponseEntity<?> handleFriendshipNotFound(FriendshipNotFoundException ex) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(FriendshipAlreadyExistsException.class)
    public ResponseEntity<?> handleFriendshipAlreadyExists(FriendshipAlreadyExistsException ex) {
        return build(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(FriendshipStatusException.class)
    public ResponseEntity<?> handleFriendshipStatus(FriendshipStatusException ex) {
        return build(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
    }

    @ExceptionHandler(ReceiverIdNotFoundException.class)
    public ResponseEntity<?> handleReceiverIdNotFound(ReceiverIdNotFoundException ex) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    //  Fallback

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneric(Exception ex) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
    }

    //  Helper

    private ResponseEntity<?> build(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(Map.of(
                "status", status.value(),
                "error", status.getReasonPhrase(),
                "message", message,
                "timestamp", Instant.now().toString()
        ));
    }
}