package com.xinbo.springboot.backend.linkoria.app.message.application.port.in;

import com.xinbo.springboot.backend.linkoria.app.message.domain.model.Message;

public interface GetLastMessageUseCase {

    GetLastMessageResponse execute(GetLastMessageQuery query);

    record GetLastMessageQuery(
            Long conversationId
    ) {}

    record GetLastMessageResponse(Message message) {}
}
