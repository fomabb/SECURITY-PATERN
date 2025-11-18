package com.iase24.crazy_task_tracker_api.businessapi.controller.handler;

import com.iase24.crazy_task_tracker_api.businessapi.dto.ChatMessageDto;
import com.iase24.crazy_task_tracker_api.businessapi.dto.request.MessageSendRequest;
import com.iase24.crazy_task_tracker_api.businessapi.service.ChatService;
import com.iase24.crazy_task_tracker_api.security.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import java.nio.file.AccessDeniedException;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public String processMessageFromClient(String message) {
        return "{\"response\" : \"" + chatService.answerMessageDump(message) + "\"}";
    }

    /**
     * Принимает новое сообщение от клиента, предназначенное для определенной чат-комнаты.
     *
     * @param roomId      ID чат-комнаты, куда отправляется сообщение.
     * @param request     DTO с текстом сообщения.
     * @param currentUser Spring Security Principal для определения отправителя.
     */
    @MessageMapping("/chat/{roomId}/sendMessage")
    public void sendMessage(@DestinationVariable UUID roomId,
                            @Payload MessageSendRequest request,
                            @AuthenticationPrincipal User currentUser
    ) throws AccessDeniedException {

        UUID senderId = currentUser.getId();
        request.setRoomId(roomId);

        log.info("Received message to room {}: {}", roomId, request.getContent());

        ChatMessageDto messageDto = chatService.sendMessage(request, senderId);

        messagingTemplate.convertAndSend("/topic/room/" + roomId, messageDto);
    }

    /**
     * (Опционально) Пример для обработки "пользователь печатает..."
     *
     * @param roomId      ID комнаты.
     * @param currentUser Пользователь, который печатает.
     */
    @MessageMapping("/chat/{roomId}/typing")
    public void userTyping(@DestinationVariable UUID roomId,
                           @AuthenticationPrincipal User currentUser
    ) {
        UUID userId = currentUser.getId();

        String userName = currentUser.getUsername();

        log.debug("User {} ({}) is typing in room {}", userName, userId, roomId);

        Map<String, String> typingEvent = Map.of(
                "userId", userId.toString(),
                "userName", userName
        );

        messagingTemplate.convertAndSend("/topic/room/" + roomId + "/typing", typingEvent);
    }
}
