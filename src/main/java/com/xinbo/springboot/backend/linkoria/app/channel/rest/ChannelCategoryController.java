package com.xinbo.springboot.backend.linkoria.app.channel.rest;

import com.xinbo.springboot.backend.linkoria.app.channel.application.port.in.CreateChannelCategoryUseCase;
import com.xinbo.springboot.backend.linkoria.app.channel.application.port.in.DeleteChannelCategoryUseCase;
import com.xinbo.springboot.backend.linkoria.app.channel.application.port.in.GetChannelCategoriesByServerIdUseCase;
import com.xinbo.springboot.backend.linkoria.app.channel.application.port.in.GetChannelCategoryUseCase;
import com.xinbo.springboot.backend.linkoria.app.channel.domain.ChannelCategory;
import com.xinbo.springboot.backend.linkoria.app.channel.rest.dto.request.CreateChannelCategoryRequest;
import com.xinbo.springboot.backend.linkoria.app.channel.rest.dto.response.ChannelCategoryResponse;
import com.xinbo.springboot.backend.linkoria.app.shared.security.AuthenticatedUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Channel Categories", description = "Gestión de categorías de canales dentro de un servidor")
@RestController
@RequestMapping("/api/v1/servers/{serverId}/categories")
public class ChannelCategoryController {

    private final CreateChannelCategoryUseCase createChannelCategoryUseCase;
    private final DeleteChannelCategoryUseCase deleteChannelCategoryUseCase;
    private final GetChannelCategoryUseCase getChannelCategoryUseCase;
    private final GetChannelCategoriesByServerIdUseCase getChannelCategoriesByServerIdUseCase;


    public ChannelCategoryController(CreateChannelCategoryUseCase createChannelCategoryUseCase, DeleteChannelCategoryUseCase deleteChannelCategoryUseCase, GetChannelCategoryUseCase getChannelCategoryUseCase, GetChannelCategoriesByServerIdUseCase getChannelCategoriesByServerIdUseCase) {
        this.createChannelCategoryUseCase = createChannelCategoryUseCase;
        this.deleteChannelCategoryUseCase = deleteChannelCategoryUseCase;
        this.getChannelCategoryUseCase = getChannelCategoryUseCase;
        this.getChannelCategoriesByServerIdUseCase = getChannelCategoriesByServerIdUseCase;
    }

    @Operation(
            summary = "Crear categoría de canal",
            description = "Crea una nueva categoría dentro del servidor indicado. Solo puede ejecutarlo el OWNER o ADMIN del servidor."
    )
    @PostMapping
    public ResponseEntity<ChannelCategoryResponse> create(
            @AuthenticationPrincipal AuthenticatedUser currentUser,
            @PathVariable Long serverId,
            @RequestBody CreateChannelCategoryRequest request) {
        ChannelCategory channelCategory = createChannelCategoryUseCase.create(
                new CreateChannelCategoryUseCase.CreateChannelCategoryCommand(currentUser.getId(), serverId, request.name()));

        return ResponseEntity.status(HttpStatus.CREATED).body(ChannelCategoryResponse.from(channelCategory));
    }

    @Operation(
            summary = "Eliminar categoría de canal",
            description = "Elimina una categoría existente. Solo puede ejecutarlo el OWNER o ADMIN del servidor."
    )
    @DeleteMapping("/{channelCategoryId}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal AuthenticatedUser currentUser,
            @PathVariable Long serverId,
            @PathVariable Long channelCategoryId) {
        deleteChannelCategoryUseCase.delete(new DeleteChannelCategoryUseCase.DeleteChannelCategoryCommand(currentUser.getId(), channelCategoryId));
        return ResponseEntity.noContent().build();
    }


    @Operation(
            summary = "Obtener categoría por id",
            description = "Devuelve el detalle de una categoría concreta. Solo accesible por miembros del servidor."
    )
    @GetMapping("/{channelCategoryId}")
    public ResponseEntity<ChannelCategoryResponse> getChannelCategory(
            @AuthenticationPrincipal AuthenticatedUser currentUser,
            @PathVariable Long serverId,
            @PathVariable Long channelCategoryId) {
        ChannelCategory channelCategory = getChannelCategoryUseCase.getChannelCategory(
                new GetChannelCategoryUseCase.GetChannelCategoryQuery(currentUser.getId(), channelCategoryId));

        return ResponseEntity.ok(ChannelCategoryResponse.from(channelCategory));
    }

    @Operation(
            summary = "Listar categorías del servidor",
            description = "Devuelve todas las categorías de canales del servidor indicado. Solo accesible por miembros del servidor."
    )
    @GetMapping()
    public ResponseEntity<List<ChannelCategoryResponse>> getByServerId(
            @AuthenticationPrincipal AuthenticatedUser currentUser,
            @PathVariable Long serverId) {
        List<ChannelCategory> channelCategories = getChannelCategoriesByServerIdUseCase.getChannelCategories(
                new GetChannelCategoriesByServerIdUseCase.GetChannelCategoriesByServerIdQuery(currentUser.getId(),serverId));

        return ResponseEntity.ok(channelCategories.stream().map(ChannelCategoryResponse::from).toList());
    }
}
