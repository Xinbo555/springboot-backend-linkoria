package com.xinbo.springboot.backend.linkoria.app.message.rest;

import com.xinbo.springboot.backend.linkoria.app.message.application.port.in.*;
import com.xinbo.springboot.backend.linkoria.app.message.domain.repository.MessageRepository;
import com.xinbo.springboot.backend.linkoria.app.message.rest.dto.response.MessagePageResponse;
import com.xinbo.springboot.backend.linkoria.app.message.rest.dto.response.MessageResponse;
import com.xinbo.springboot.backend.linkoria.app.shared.security.AuthenticatedUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Messages", description = "Gestión de mensajes — envío, edición, eliminación y recuperación del historial")
@RestController
@RequestMapping("/api/v1/conversations/{conversationId}/messages")
public class MessageController {

    private final GetMessagesUseCase getMessagesUseCase;
    private final GetLastMessageUseCase getLastMessageUseCase;

    public MessageController(GetMessagesUseCase getMessagesUseCase, GetLastMessageUseCase getLastMessageUseCase) {
        this.getMessagesUseCase = getMessagesUseCase;
        this.getLastMessageUseCase = getLastMessageUseCase;
    }


    @Operation(
            summary = "Obtener historial de mensajes",
            description = "Recupera mensajes de una conversación con pagination basada en cursor. " +
                    "El parámetro 'cursor' es el ID del último mensaje visto (null = primera página). " +
                    "El parámetro 'direction' indica si obtener mensajes anteriores (BACKWARDS) o posteriores (FORWARDS). " +
                    "Los resultados incluyen un 'nextCursor' para la siguiente página y un flag 'hasMore' para indicar si hay más mensajes."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Historial de mensajes recuperado exitosamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MessagePageResponse.class)
            )
    )
    @GetMapping
    public ResponseEntity<MessagePageResponse> getMessages(
            @AuthenticationPrincipal AuthenticatedUser currentUser,
            @PathVariable Long conversationId,
            @Parameter(description = "ID del último mensaje visto (null para primera página)")
            @RequestParam(required = false) Long cursor,
            @Parameter(description = "Cantidad de mensajes a retornar (default: 50)")
            @RequestParam(defaultValue = "50") int limit,
            @Parameter(description = "Dirección de pagination: BACKWARDS (hacia atrás en el tiempo) o FORWARDS (hacia adelante)")
            @RequestParam(defaultValue = "BACKWARDS") String direction) {

        MessageRepository.PaginationDirection paginationDirection =
                MessageRepository.PaginationDirection.valueOf(direction.toUpperCase());

        GetMessagesUseCase.GetMessagesQuery query = new GetMessagesUseCase.GetMessagesQuery(
                conversationId,
                cursor,
                limit,
                paginationDirection
        );

        GetMessagesUseCase.GetMessagesResponse response = getMessagesUseCase.execute(query);

        MessagePageResponse pageResponse = new MessagePageResponse(
                response.messages().stream().map(MessageResponse::from).toList(),
                response.nextCursor(),
                response.hasMore()
        );

        return ResponseEntity.ok(pageResponse);
    }

    @Operation(
            summary = "Obtener último mensaje",
            description = "Recupera el mensaje más reciente de una conversación. " +
                    "Útil para mostrar un preview en el listado de conversaciones. " +
                    "Retorna null si la conversación no tiene mensajes aún."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Último mensaje recuperado exitosamente",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MessageResponse.class)
            )
    )
    @GetMapping("/last")
    public ResponseEntity<MessageResponse> getLastMessage(
            @AuthenticationPrincipal AuthenticatedUser currentUser,
            @PathVariable Long conversationId) {

        GetLastMessageUseCase.GetLastMessageQuery query = new GetLastMessageUseCase.GetLastMessageQuery(
                conversationId
        );

        GetLastMessageUseCase.GetLastMessageResponse response = getLastMessageUseCase.execute(query);

        if (response.message() == null) {
            return ResponseEntity.noContent().build();
        }

        MessageResponse messageResponse = MessageResponse.from(response.message());
        return ResponseEntity.ok(messageResponse);
    }
}
