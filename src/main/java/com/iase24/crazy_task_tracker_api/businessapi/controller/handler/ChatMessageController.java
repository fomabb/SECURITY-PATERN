package com.iase24.crazy_task_tracker_api.businessapi.controller.handler;

import com.iase24.crazy_task_tracker_api.businessapi.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatService chatService;

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public String processMessageFromClient(String message) {
        return "{\"response\" : \"" + chatService.answerMessageDump(message) + "\"}";
    }
}
