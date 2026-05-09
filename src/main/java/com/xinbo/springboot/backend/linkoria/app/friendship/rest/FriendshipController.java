package com.xinbo.springboot.backend.linkoria.app.friendship.rest;

import com.xinbo.springboot.backend.linkoria.app.friendship.domain.Friendship;
import com.xinbo.springboot.backend.linkoria.app.shared.security.AuthenticatedUser;
import com.xinbo.springboot.backend.linkoria.app.friendship.application.port.in.*;
import com.xinbo.springboot.backend.linkoria.app.friendship.rest.dto.FriendshipActionRequest;
import com.xinbo.springboot.backend.linkoria.app.friendship.rest.dto.FriendshipResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Friendships", description = "Gestión de amistades — solicitudes, aceptación, bloqueos y listados")
@RestController
@RequestMapping("/api/v1/friendships")
public class FriendshipController {
    private final AcceptFriendRequestUseCase acceptFriendRequestUseCase;
    private final DeclineFriendRequestUseCase declineFriendRequestUseCase;
    private final GetFriendshipsUseCase getFriendshipsUseCase;
    private final RemoveFriendUseCase removeFriendUseCase;
    private final SendFriendRequestUseCase sendFriendRequestUseCase;

    public FriendshipController(AcceptFriendRequestUseCase acceptFriendRequestUseCase,
                                DeclineFriendRequestUseCase declineFriendRequestUseCase,
                                GetFriendshipsUseCase getFriendshipsUseCase,
                                RemoveFriendUseCase removeFriendUseCase,
                                SendFriendRequestUseCase sendFriendRequestUseCase) {
        this.acceptFriendRequestUseCase = acceptFriendRequestUseCase;
        this.declineFriendRequestUseCase = declineFriendRequestUseCase;
        this.getFriendshipsUseCase = getFriendshipsUseCase;
        this.removeFriendUseCase = removeFriendUseCase;
        this.sendFriendRequestUseCase = sendFriendRequestUseCase;
    }

    @Operation(
            summary = "Enviar solicitud de amistad",
            description = "Envía una solicitud de amistad al usuario indicado. El remitente se extrae del token."
    )
    @PostMapping
    public ResponseEntity<Void> send(
            @AuthenticationPrincipal AuthenticatedUser currentUser, // Principal inyectado por BearerTokenAuthenticationFilter vía SecurityContext
            @RequestBody FriendshipActionRequest request) {
        Friendship friendship = sendFriendRequestUseCase.send(new SendFriendRequestUseCase.SendCommand(currentUser.getId(), request.targetId()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "Aceptar solicitud de amistad",
            description = "Acepta una solicitud de amistad pendiente. Solo puede ejecutarlo el receptor de la solicitud."
    )
    @PatchMapping("/{targetId}/accept")
    public ResponseEntity<Void> accept(
            @AuthenticationPrincipal AuthenticatedUser currentUser,
            @PathVariable UUID targetId) {
        Friendship friendship = acceptFriendRequestUseCase.accept(new AcceptFriendRequestUseCase.AcceptCommand(currentUser.getId(), targetId));
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Rechazar solicitud de amistad",
            description = "Rechaza una solicitud de amistad pendiente. Solo puede ejecutarlo el receptor de la solicitud."
    )
    @PatchMapping("/{targetId}/decline")
    public ResponseEntity<Void> decline(
            @AuthenticationPrincipal AuthenticatedUser currentUser,
            @PathVariable UUID targetId) {
        Friendship friendship = declineFriendRequestUseCase.decline(new DeclineFriendRequestUseCase.DeclineCommand(currentUser.getId(), targetId));
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Eliminar amigo",
            description = "Elimina una amistad activa o bloqueada. Cualquiera de los dos puede ejecutarlo."
    )
    @PatchMapping("/{targetId}/remove")
    public ResponseEntity<Void> remove(
            @AuthenticationPrincipal AuthenticatedUser currentUser,
            @PathVariable UUID targetId) {
        Friendship friendship = removeFriendUseCase.remove(new RemoveFriendUseCase.RemoveCommand(currentUser.getId(), targetId));
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Listar amigos",
            description = "Devuelve la lista de amigos con estado ACCEPTED. Los bloqueados por otros aparecen enmascarados como ACCEPTED."
    )
    @GetMapping("/friends")
    public ResponseEntity<List<FriendshipResponse>> getFriends(
            @AuthenticationPrincipal AuthenticatedUser currentUser) {
        List<FriendshipResponse> friendshipResponseList = getFriendshipsUseCase.getFriends(currentUser.getId())
                .stream().map(FriendshipResponse::from).toList();

        return ResponseEntity.ok(friendshipResponseList);
    }

    @Operation(
            summary = "Solicitudes recibidas pendientes",
            description = "Devuelve las solicitudes de amistad pendientes que el usuario ha recibido y aún no ha respondido."
    )
    @GetMapping("/pending/received")
    public ResponseEntity<List<FriendshipResponse>> getPendingReceived(
            @AuthenticationPrincipal AuthenticatedUser currentUser) {
        List<FriendshipResponse> friendshipResponseList = getFriendshipsUseCase.getPendingReceived(currentUser.getId())
                .stream().map(FriendshipResponse::from).toList();

        return ResponseEntity.ok(friendshipResponseList);
    }

    @Operation(
            summary = "Solicitudes enviadas pendientes",
            description = "Devuelve las solicitudes de amistad que el usuario ha enviado y están esperando respuesta."
    )
    @GetMapping("/pending/sent")
    public ResponseEntity<List<FriendshipResponse>> getPendingSent(
            @AuthenticationPrincipal AuthenticatedUser currentUser) {
        List<FriendshipResponse> friendshipResponseList = getFriendshipsUseCase.getPendingSent(currentUser.getId())
                .stream().map(FriendshipResponse::from).toList();

        return ResponseEntity.ok(friendshipResponseList);
    }
}
