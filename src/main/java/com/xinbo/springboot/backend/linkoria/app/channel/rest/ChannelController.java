package com.xinbo.springboot.backend.linkoria.app.channel.rest;

import com.xinbo.springboot.backend.linkoria.app.channel.application.port.in.*;
import com.xinbo.springboot.backend.linkoria.app.channel.domain.Channel;
import com.xinbo.springboot.backend.linkoria.app.channel.rest.dto.request.CreateChannelRequest;
import com.xinbo.springboot.backend.linkoria.app.channel.rest.dto.response.ChannelResponse;
import com.xinbo.springboot.backend.linkoria.app.shared.security.AuthenticatedUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Channels", description = "Gestión de canales dentro de un servidor")
@RestController
@RequestMapping("/api/v1/servers/{serverId}/channels")
public class ChannelController {

    private final CreateChannelUseCase createChannelUseCase;
    private final DeleteChannelUseCase deleteChannelUseCase;
    private final GetChannelsByCategoryIdUseCase getChannelsByCategoryIdUseCase;
    private final GetChannelsByServerIdUseCase getChannelsByServerIdUseCase;
    private final GetChannelUseCase getChannelUseCase;

    public ChannelController(CreateChannelUseCase createChannelUseCase, DeleteChannelUseCase deleteChannelUseCase, GetChannelsByCategoryIdUseCase getChannelsByCategoryIdUseCase, GetChannelsByServerIdUseCase getChannelsByServerIdUseCase, GetChannelUseCase getChannelUseCase) {
        this.createChannelUseCase = createChannelUseCase;
        this.deleteChannelUseCase = deleteChannelUseCase;
        this.getChannelsByCategoryIdUseCase = getChannelsByCategoryIdUseCase;
        this.getChannelsByServerIdUseCase = getChannelsByServerIdUseCase;
        this.getChannelUseCase = getChannelUseCase;
    }

    @Operation(
            summary = "Crear canal",
            description = "Crea un nuevo canal dentro del servidor indicado. Opcionalmente puede asignarse a una categoría. Solo puede ejecutarlo el OWNER o ADMIN del servidor."
    )
    @PostMapping
    public ResponseEntity<ChannelResponse> create(
            @AuthenticationPrincipal AuthenticatedUser currentUser,
            @PathVariable Long serverId,
            @RequestBody CreateChannelRequest request) {
        Channel channel = createChannelUseCase.create(new CreateChannelUseCase.CreateChannelCommand(
                currentUser.getId(), serverId, request.ChannelCategoryId(), request.name()));

        return ResponseEntity.status(HttpStatus.CREATED).body(ChannelResponse.from(channel));
    }

    @Operation(
            summary = "Eliminar canal",
            description = "Elimina un canal existente. Solo puede ejecutarlo el OWNER o ADMIN del servidor."
    )
    @DeleteMapping("/{channelId}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal AuthenticatedUser currentUser,
            @PathVariable Long serverId,
            @PathVariable Long channelId) {
        deleteChannelUseCase.delete(new DeleteChannelUseCase.DeleteChannelCommand(currentUser.getId(), channelId));
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Listar canales",
            description = "Devuelve los canales del servidor. Si se proporciona categoryId(?categoryId = \"\") filtra por categoría, si no devuelve todos los canales del servidor. Solo accesible por miembros del servidor."
    )
    @GetMapping
    public ResponseEntity<List<ChannelResponse>> getByCategory(
            @AuthenticationPrincipal AuthenticatedUser currentUser,
            @PathVariable Long serverId,
            @RequestParam(required = false) Long categoryId) {

        List<Channel> channels;
        if (categoryId == null) {
            channels = getChannelsByServerIdUseCase.getChannels(new GetChannelsByServerIdUseCase.GetChannelsByServerIdQuery(currentUser.getId(),serverId));
        } else {
            channels = getChannelsByCategoryIdUseCase.getChannels(new GetChannelsByCategoryIdUseCase.GetChannelsByCategoryIdQuery(currentUser.getId(),categoryId));
        }
        return ResponseEntity.ok(channels.stream().map(ChannelResponse::from).toList());
    }

    @Operation(
            summary = "Obtener canal por id",
            description = "Devuelve el detalle de un canal concreto. Solo accesible por miembros del servidor."
    )
    @GetMapping("/{channelId}")
    public ResponseEntity<ChannelResponse> getChannel(
            @AuthenticationPrincipal AuthenticatedUser currentUser,
            @PathVariable Long serverId,
            @PathVariable Long channelId) {
        Channel channel = getChannelUseCase.getChannel(new GetChannelUseCase.GetChannelQuery(currentUser.getId(),channelId));
        return ResponseEntity.ok(ChannelResponse.from(channel));
    }
}
