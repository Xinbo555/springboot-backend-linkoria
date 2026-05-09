package com.xinbo.springboot.backend.linkoria.app.message.application.port.in;

import com.xinbo.springboot.backend.linkoria.app.message.domain.model.Message;
import com.xinbo.springboot.backend.linkoria.app.message.domain.repository.MessageRepository;

import java.util.List;

public interface GetMessagesUseCase {
    GetMessagesResponse execute(GetMessagesQuery query);

    record GetMessagesQuery(Long conversationId, Long cursor, int limit, MessageRepository.PaginationDirection direction) {}
    record GetMessagesResponse(List<Message> messages, Long nextCursor, boolean hasMore) {}
}
