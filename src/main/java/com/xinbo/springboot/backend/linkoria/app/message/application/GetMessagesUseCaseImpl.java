package com.xinbo.springboot.backend.linkoria.app.message.application;

import com.xinbo.springboot.backend.linkoria.app.message.application.port.in.GetMessagesUseCase;
import com.xinbo.springboot.backend.linkoria.app.message.domain.model.Message;
import com.xinbo.springboot.backend.linkoria.app.message.domain.repository.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GetMessagesUseCaseImpl implements GetMessagesUseCase {

    private final MessageRepository messageRepository;

    public GetMessagesUseCaseImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public GetMessagesResponse execute(GetMessagesQuery query) {

        // Obtener limit+1 mensajes para detectar si hay más disponibles
        int fetchLimit = query.limit() + 1;

        List<Message> messages = messageRepository.findByConversationIdCursorPaginated(
                query.conversationId(),
                query.cursor(),
                fetchLimit,
                query.direction()
        );

        // Determinar si hay más mensajes y extraer el nextCursor
        boolean hasMore = messages.size() > query.limit();
        Long nextCursor = null;

        if (hasMore) {
            // Si hay más, el nextCursor es el ID del último mensaje en la respuesta
            nextCursor = messages.get(query.limit() - 1).getId();
            // Remover el mensaje extra (limit+1)
            messages = messages.subList(0, query.limit());
        }

        return new GetMessagesResponse(
                messages,
                nextCursor,
                hasMore
        );
    }
}
