package com.xinbo.springboot.backend.linkoria.app.controllers;

import com.xinbo.springboot.backend.linkoria.app.models.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@Controller
public class ChatController {

    //un usuario envia un mensaje y lo devuelve a todos los uduarios
    @MessageMapping("/chat/{chatId}")
    @SendTo("/topic/chat/{chatId}")
    public Message receiveMessage(@DestinationVariable String chatId, Message message){
        // Validación básica
        if(message.getMessage() == null || message.getMessage().trim().isEmpty()) {
            throw new IllegalArgumentException("El mensaje no puede estar vacío");
        }
        if(message.getFrom() == null || message.getFrom().trim().isEmpty()) {
            throw new IllegalArgumentException("El campo 'from' es obligatorio");
        }

        // Inicialización de campos
        message.setId(UUID.randomUUID().toString());
        message.setTimeStamp(Instant.now());
        if(message.getStatus() == null) message.setStatus("sent");
        if(message.getReadBy() == null) message.setReadBy(new ArrayList<>());
        if(message.getType() == null) message.setType("text");

        return message;
    }
}
