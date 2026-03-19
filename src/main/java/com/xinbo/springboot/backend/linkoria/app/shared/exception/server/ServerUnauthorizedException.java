package com.xinbo.springboot.backend.linkoria.app.shared.exception.server;

import com.xinbo.springboot.backend.linkoria.app.shared.exception.UnauthorizedException;

public class ServerUnauthorizedException extends UnauthorizedException {
    public ServerUnauthorizedException(String message) {
        super(message);
    }
}
