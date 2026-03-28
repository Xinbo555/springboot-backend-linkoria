package com.xinbo.springboot.backend.linkoria.app.channel.infrastructure.adapter.out;

import com.xinbo.springboot.backend.linkoria.app.channel.application.port.out.ConversationCreationPort;
import com.xinbo.springboot.backend.linkoria.app.conversation.application.ConversationFacade;
import org.springframework.stereotype.Component;

@Component
public class ConversationCreationAdapter implements ConversationCreationPort {

    private final ConversationFacade conversationFacade;

    public ConversationCreationAdapter(ConversationFacade conversationFacade) {
        this.conversationFacade = conversationFacade;
    }

    @Override
    public void createForChannel(Long channelId) {
        conversationFacade.createForChannel(channelId);
    }
}