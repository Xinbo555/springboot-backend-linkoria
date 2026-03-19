package com.xinbo.springboot.backend.linkoria.app.server.rest;

import com.xinbo.springboot.backend.linkoria.app.server.application.ServerMemberDetail;
import com.xinbo.springboot.backend.linkoria.app.server.application.port.in.*;
import com.xinbo.springboot.backend.linkoria.app.server.rest.dto.request.UpdateMemberRoleRequest;
import com.xinbo.springboot.backend.linkoria.app.server.rest.dto.response.ServerMemberResponse;
import com.xinbo.springboot.backend.linkoria.app.shared.security.AuthenticatedUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Server Members", description = "Gestión de miembros de un servidor — listado, expulsión y cambio de roles")
@RestController
@RequestMapping("/api/v1/servers/{serverId}/members")
public class ServerMemberController {
    private final GetServerMembersUseCase getServerMembersUseCase;
    private final KickMemberUseCase kickMemberUseCase;
    private final UpdateMemberRoleUseCase updateMemberRoleUseCase;

    public ServerMemberController(GetServerMembersUseCase getServerMembersUseCase, KickMemberUseCase kickMemberUseCase, UpdateMemberRoleUseCase updateMemberRoleUseCase) {
        this.getServerMembersUseCase = getServerMembersUseCase;
        this.kickMemberUseCase = kickMemberUseCase;
        this.updateMemberRoleUseCase = updateMemberRoleUseCase;
    }

    @Operation(
            summary = "Listar miembros",
            description = "Devuelve la lista de miembros del servidor con su nombre de usuario, avatar y rol. Solo accesible si el usuario autenticado es miembro del servidor."
    )
    @GetMapping
    public ResponseEntity<List<ServerMemberResponse>> getMembers(
            @AuthenticationPrincipal AuthenticatedUser currentUser,
            @PathVariable Long serverId){
        List<ServerMemberDetail> serverMemberDetails = getServerMembersUseCase
                .getMembers(new GetServerMembersUseCase.GetMembersQuery(currentUser.getId(),serverId));

        List<ServerMemberResponse> serverMemberResponses = serverMemberDetails
                .stream().map(ServerMemberResponse::from).toList();

        return ResponseEntity.ok(serverMemberResponses);
    }

    @Operation(
            summary = "Expulsar miembro",
            description = "Expulsa a un miembro del servidor. Solo puede ejecutarlo el OWNER o un ADMIN. No se puede expulsar al OWNER."
    )
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> kickMember(
            @AuthenticationPrincipal AuthenticatedUser currentUser,
            @PathVariable Long serverId,
            @PathVariable UUID userId) {
        kickMemberUseCase.kick(new KickMemberUseCase.KickCommand(currentUser.getId(), userId, serverId));
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Cambiar rol de miembro",
            description = "Cambia el rol de un miembro del servidor. Solo puede ejecutarlo el OWNER. Si el nuevo rol es OWNER se produce una transferencia de propiedad — el OWNER actual pasa a ADMIN automáticamente."
    )
    @PatchMapping("/{userId}/role")
    public ResponseEntity<ServerMemberResponse> updateMemberRole(
            @AuthenticationPrincipal AuthenticatedUser currentUser,
            @PathVariable Long serverId,
            @PathVariable UUID userId,
            @RequestBody UpdateMemberRoleRequest request) {
        ServerMemberDetail member = updateMemberRoleUseCase
                .update(new UpdateMemberRoleUseCase.UpdateRoleCommand(currentUser.getId(),userId,serverId,request.newRole()));

        return ResponseEntity.ok(ServerMemberResponse.from(member));
    }
}
