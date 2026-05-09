package com.xinbo.springboot.backend.linkoria.app.message.infrastructure.messaging;

import com.xinbo.springboot.backend.linkoria.app.message.domain.event.MessageDomainEvent;
import com.xinbo.springboot.backend.linkoria.app.message.domain.event.MessageEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class MessageEventPublisherImpl implements MessageEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public MessageEventPublisherImpl(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publish(MessageDomainEvent event) {

        // Publicar el evento a todos los listeners registrados en Spring
        applicationEventPublisher.publishEvent(event);
    }
}
