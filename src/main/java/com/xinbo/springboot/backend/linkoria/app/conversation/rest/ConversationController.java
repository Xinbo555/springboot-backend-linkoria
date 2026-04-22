package com.xinbo.springboot.backend.linkoria.app.conversation.rest;

import com.xinbo.springboot.backend.linkoria.app.conversation.application.port.in.CreateDmConversationUseCase;
import com.xinbo.springboot.backend.linkoria.app.conversation.application.port.in.GetChannelConversationUseCase;
import com.xinbo.springboot.backend.linkoria.app.conversation.application.port.in.GetConversationsByUserUseCase;
import com.xinbo.springboot.backend.linkoria.app.conversation.application.port.in.GetDmConversationUseCase;
import com.xinbo.springboot.backend.linkoria.app.conversation.rest.dto.request.CreateDmRequest;
import com.xinbo.springboot.backend.linkoria.app.conversation.rest.dto.response.ConversationResponse;
import com.xinbo.springboot.backend.linkoria.app.shared.security.AuthenticatedUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Conversations", description = "Gestión de conversaciones directas entre usuarios")
@RestController
@RequestMapping("/api/v1/conversations")
public class ConversationController {

    private final CreateDmConversationUseCase createDmConversationUseCase;
    private final GetConversationsByUserUseCase getConversationsByUserUseCase;
    private final GetDmConversationUseCase getDmConversationUseCase;
    private final GetChannelConversationUseCase getChannelConversationUseCase;

    public ConversationController(CreateDmConversationUseCase createDmConversationUseCase,
                                  GetConversationsByUserUseCase getConversationsByUserUseCase,
                                  GetDmConversationUseCase getDmConversationUseCase, GetChannelConversationUseCase getChannelConversationUseCase) {
        this.createDmConversationUseCase = createDmConversationUseCase;
        this.getConversationsByUserUseCase = getConversationsByUserUseCase;
        this.getDmConversationUseCase = getDmConversationUseCase;
        this.getChannelConversationUseCase = getChannelConversationUseCase;
    }

    @Operation(
            summary = "Crear conversación directa",
            description = "Crea una conversación directa con otro usuario. Si ya existe una conversación entre ambos usuarios la devuelve sin crear una nueva."
    )
    @PostMapping("/dm")
    public ResponseEntity<ConversationResponse> createDm(
            @AuthenticationPrincipal AuthenticatedUser currentUser,
            @RequestBody CreateDmRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ConversationResponse.from(
                        createDmConversationUseCase.execute(
                                new CreateDmConversationUseCase.CreateDmConversationCommand(currentUser.getId(), request.targetId())
                        )
                )
        );
    }

    @Operation(
            summary = "Listar conversaciones directas",
            description = "Devuelve todas las conversaciones directas del usuario autenticado."
    )
    @GetMapping("/dm")
    public ResponseEntity<List<ConversationResponse>> getMyDms(
            @AuthenticationPrincipal AuthenticatedUser currentUser) {
        return ResponseEntity.ok(
                getConversationsByUserUseCase.execute(
                                new GetConversationsByUserUseCase.GetConversationsByUserQuery(currentUser.getId())
                        ).stream()
                        .map(ConversationResponse::from)
                        .toList()
        );
    }

    @Operation(
            summary = "Obtener conversación directa por usuario",
            description = "Devuelve la conversación directa entre el usuario autenticado y el usuario indicado."
    )
    @GetMapping("/dm/{targetId}")
    public ResponseEntity<ConversationResponse> getDm(
            @AuthenticationPrincipal AuthenticatedUser currentUser,
            @PathVariable UUID targetId) {
        return ResponseEntity.ok(
                ConversationResponse.from(
                        getDmConversationUseCase.execute(
                                new GetDmConversationUseCase.GetDmConversationQuery(currentUser.getId(), targetId)
                        )
                )
        );
    }

    @Operation(
            summary = "Obtener conversación del canal",
            description = "Devuelve la conversación del canal especificado. Solo los miembros del canal pueden acceder."
    )
    @GetMapping("/channel/{channelId}")
    public ResponseEntity<ConversationResponse> getChannelConversation(
            @AuthenticationPrincipal AuthenticatedUser currentUser,
            @PathVariable Long channelId) {
        return ResponseEntity.ok(
                ConversationResponse.from(
                        getChannelConversationUseCase.execute(
                                new GetChannelConversationUseCase.GetChannelConversationQuery(currentUser.getId(), channelId)
                        )
                )
        );
    }
}