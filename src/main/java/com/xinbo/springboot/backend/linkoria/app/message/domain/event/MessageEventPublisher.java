package com.xinbo.springboot.backend.linkoria.app.message.domain.event;

public interface MessageEventPublisher {
    void publish(MessageDomainEvent event);
}
