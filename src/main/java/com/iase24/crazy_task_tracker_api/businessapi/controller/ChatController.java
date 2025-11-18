package com.iase24.crazy_task_tracker_api.businessapi.controller;

import com.iase24.crazy_task_tracker_api.businessapi.dto.ChatMessageDto;
import com.iase24.crazy_task_tracker_api.businessapi.dto.GroupChatDetailsDto;
import com.iase24.crazy_task_tracker_api.businessapi.dto.GroupChatListDto;
import com.iase24.crazy_task_tracker_api.businessapi.dto.request.GroupChatCreateRequest;
import com.iase24.crazy_task_tracker_api.businessapi.service.ChatService;
import com.iase24.crazy_task_tracker_api.businessapi.service.GroupChatService;
import com.iase24.crazy_task_tracker_api.security.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;
import java.util.Map;
import java.util.UUID;

import static com.iase24.crazy_task_tracker_api.util.path.RestPathApi.CHAT_API;

@RestController
@RequestMapping(CHAT_API)
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Chat API", description = "`Интерфейс для работы с чатами`")
public class ChatController {

    private final ChatService chatService;
    private final GroupChatService groupChatService;

    @GetMapping
    public ResponseEntity<Page<GroupChatListDto>> getAllGroups(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(groupChatService.getAllGroups(pageable));
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<GroupChatDetailsDto> getGroupById(@PathVariable UUID groupId) {
        return ResponseEntity.ok(groupChatService.getGroupById(groupId));
    }

    @PostMapping
    public ResponseEntity<GroupChatDetailsDto> createGroup(
            @Valid @RequestBody GroupChatCreateRequest request,
            @AuthenticationPrincipal User currentUser
    ) {
        UUID currentUserId = currentUser.getId();
        GroupChatDetailsDto createdGroup = groupChatService.createGroup(request, currentUserId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGroup);
    }

    @Operation(
            summary = "Получить историю сообщений для чат-комнаты",
            description = "Возвращает страницу с сообщениями для указанной комнаты. " +
                    "Используется для начальной загрузки и для 'бесконечной прокрутки' истории."
    )
    @ApiResponse(responseCode = "200", description = "Страница с сообщениями 1успешно получена.")
    @ApiResponse(responseCode = "403", description = "Доступ запрещен (пользователь не является участником чата).")
    @GetMapping("/{roomId}/messages")
    public ResponseEntity<Page<ChatMessageDto>> getChatMessages(
            @Parameter(description = "ID чат-комнаты", required = true)
            @PathVariable UUID roomId,
            @AuthenticationPrincipal User currentUser,
            @ParameterObject
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) throws AccessDeniedException {
        Page<ChatMessageDto> messages = chatService.getMessagesForRoom(roomId, currentUser.getId(), pageable);
        return ResponseEntity.ok(messages);
    }

    @Operation(
            summary = "Найти или создать приватный чат",
            description = "Находит существующий приватный чат с указанным пользователем или создает новый, если чата еще нет."
    )
    @ApiResponse(responseCode = "200", description = "Чат успешно найден или создан. Возвращается ID комнаты.")
    @PostMapping("/private/{recipientId}")
    public ResponseEntity<Map<String, UUID>> findOrCreatePrivateChat(
            @Parameter(description = "ID пользователя, с которым нужно начать чат", required = true)
            @PathVariable UUID recipientId,

            @AuthenticationPrincipal User currentUser
    ) {
        UUID roomId = chatService.findOrCreatePrivateRoom(currentUser.getId(), recipientId);
        return ResponseEntity.ok(Map.of("roomId", roomId));
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<Void> deleteGroup(
            @PathVariable UUID groupId,
            @AuthenticationPrincipal User currentUser
    ) {
        UUID currentUserId = currentUser.getId();
        groupChatService.deleteGroup(groupId, currentUserId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{groupId}/join")
    public ResponseEntity<Void> joinGroup(
            @PathVariable UUID groupId,
            @AuthenticationPrincipal User currentUser
    ) {
        UUID currentUserId = currentUser.getId();
        groupChatService.joinGroup(groupId, currentUserId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{groupId}/leave")
    public ResponseEntity<Void> leaveGroup(
            @PathVariable UUID groupId,
            @AuthenticationPrincipal User currentUser
    ) {
        UUID currentUserId = currentUser.getId();
        groupChatService.leaveGroup(groupId, currentUserId);
        return ResponseEntity.noContent().build();
    }
}
