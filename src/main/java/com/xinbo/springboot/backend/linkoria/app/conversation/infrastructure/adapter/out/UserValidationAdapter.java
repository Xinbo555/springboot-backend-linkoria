package com.xinbo.springboot.backend.linkoria.app.conversation.infrastructure.adapter.out;

import com.xinbo.springboot.backend.linkoria.app.conversation.application.port.out.UserValidationPort;
import com.xinbo.springboot.backend.linkoria.app.user.application.UserFacade;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("conversationUserValidationAdapter")
public class UserValidationAdapter implements UserValidationPort {

    private final UserFacade userFacade;

    public UserValidationAdapter(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @Override
    public void validateExists(UUID userId) {
        userFacade.validateExists(userId);
    }
}