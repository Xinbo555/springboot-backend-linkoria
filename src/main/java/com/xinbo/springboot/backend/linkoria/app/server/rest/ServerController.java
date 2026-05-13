package com.xinbo.springboot.backend.linkoria.app.server.rest;


import com.xinbo.springboot.backend.linkoria.app.server.application.port.in.*;
import com.xinbo.springboot.backend.linkoria.app.server.domain.Server;
import com.xinbo.springboot.backend.linkoria.app.server.rest.dto.request.CreateServerRequest;
import com.xinbo.springboot.backend.linkoria.app.server.rest.dto.request.JoinServerRequest;
import com.xinbo.springboot.backend.linkoria.app.server.rest.dto.request.UpdateServerRequest;
import com.xinbo.springboot.backend.linkoria.app.server.rest.dto.response.ServerResponse;
import com.xinbo.springboot.backend.linkoria.app.shared.security.AuthenticatedUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Servers", description = "Gestión de servidores — creación, actualización, eliminación y membresía")
@RestController
@RequestMapping("/api/v1/servers")
public class ServerController {
    private final CreateServerUseCase createServerUseCase;
    private final DeleteServerUseCase deleteServerUseCase;
    private final GetServerUseCase getServerUseCase;
    private final UpdateServerUseCase updateServerUseCase;
    private final JoinServerUseCase joinServerUseCase;
    private final LeaveServerUseCase leaveServerUseCase;
    private final GetServersByUserIdUseCase getServersByUserIdUseCase;

    public ServerController(CreateServerUseCase createServerUseCase, DeleteServerUseCase deleteServerUseCase, GetServerUseCase getServerUseCase, UpdateServerUseCase updateServerUseCase, JoinServerUseCase joinServerUseCase, LeaveServerUseCase leaveServerUseCase, GetServersByUserIdUseCase getServersByUserIdUseCase) {
        this.createServerUseCase = createServerUseCase;
        this.deleteServerUseCase = deleteServerUseCase;
        this.getServerUseCase = getServerUseCase;
        this.updateServerUseCase = updateServerUseCase;
        this.joinServerUseCase = joinServerUseCase;
        this.leaveServerUseCase = leaveServerUseCase;
        this.getServersByUserIdUseCase = getServersByUserIdUseCase;
    }

    @Operation(
            summary = "Crear servidor",
            description = "Crea un nuevo servidor y asigna automáticamente al usuario autenticado como OWNER. Genera un inviteCode único para que otros usuarios puedan unirse."
    )
    @PostMapping
    public ResponseEntity<ServerResponse> createServer(
            @AuthenticationPrincipal AuthenticatedUser currentUser,
            @RequestBody CreateServerRequest request) {
        Server server = createServerUseCase.create(new CreateServerUseCase.CreateServerCommand(currentUser.getId(), request.name(), request.iconUrl()));
        return ResponseEntity.status(HttpStatus.CREATED).body(ServerResponse.from(server));
    }

    @Operation(
            summary = "Obtener servidor",
            description = "Devuelve los datos de un servidor. Solo accesible si el usuario autenticado es miembro del servidor."
    )
    @GetMapping("/{serverId}")
    public ResponseEntity<ServerResponse> getServer(
            @AuthenticationPrincipal AuthenticatedUser currentUser,
            @PathVariable Long serverId) {
        Server server = getServerUseCase.getServer(new GetServerUseCase.GetServerQuery(currentUser.getId(), serverId));
        return ResponseEntity.ok(ServerResponse.from(server));
    }

    @Operation(
            summary = "Obtener servidores del usuario",
            description = "Lista todos los servidores del usuario autenticado"
    )
    @GetMapping
    public ResponseEntity<List<ServerResponse>> getServers(
            @AuthenticationPrincipal AuthenticatedUser currentUser) {
        List<Server> servers = getServersByUserIdUseCase.getServers(new GetServersByUserIdUseCase.GetServersQuery(currentUser.getId()));
        return ResponseEntity.ok(servers.stream().map(ServerResponse::from).toList());
    }

    @Operation(
            summary = "Actualizar servidor",
            description = "Actualiza parcialmente los datos del servidor. Solo puede ejecutarlo el OWNER o un ADMIN. Los campos no enviados se mantienen sin cambios."
    )
    @PatchMapping("/{serverId}")
    public ResponseEntity<ServerResponse> updateServer(
            @AuthenticationPrincipal AuthenticatedUser currentUser,
            @RequestBody UpdateServerRequest request,
            @PathVariable Long serverId) {
        Server server = updateServerUseCase.update(new UpdateServerUseCase.UpdateCommand(serverId, currentUser.getId(), request.name(), request.iconUrl()));
        return ResponseEntity.ok(ServerResponse.from(server));
    }

    @Operation(
            summary = "Unirse a un servidor",
            description = "Une al usuario autenticado al servidor asociado al inviteCode proporcionado. El usuario recibe el rol MEMBER automáticamente."
    )
    @PostMapping("/join")
    public ResponseEntity<ServerResponse> joinServer(
            @AuthenticationPrincipal AuthenticatedUser currentUser,
            @RequestBody JoinServerRequest request) {
        Server server = joinServerUseCase.join(new JoinServerUseCase.JoinCommand(currentUser.getId(), request.inviteCode()));
        return ResponseEntity.ok(ServerResponse.from(server));
    }

    @Operation(
            summary = "Abandonar servidor",
            description = "El usuario autenticado abandona el servidor. El OWNER no puede abandonarlo — debe transferir la propiedad previamente."
    )
    @DeleteMapping("/{serverId}/leave")
    public ResponseEntity<Void> leaveServer(
            @AuthenticationPrincipal AuthenticatedUser currentUser,
            @PathVariable Long serverId) {
        leaveServerUseCase.leave(new LeaveServerUseCase.LeaveCommand(currentUser.getId(), serverId));
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Eliminar servidor",
            description = "Elimina permanentemente el servidor y todos sus miembros. Solo puede ejecutarlo el OWNER."
    )
    @DeleteMapping("/{serverId}")
    public ResponseEntity<Void> deleteServer(
            @AuthenticationPrincipal AuthenticatedUser currentUser,
            @PathVariable Long serverId) {
        deleteServerUseCase.delete(new DeleteServerUseCase.DeleteCommand(currentUser.getId(), serverId));
        return ResponseEntity.noContent().build();
    }
}
