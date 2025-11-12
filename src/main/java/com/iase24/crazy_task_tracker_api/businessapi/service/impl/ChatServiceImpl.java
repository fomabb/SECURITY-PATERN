package com.iase24.crazy_task_tracker_api.businessapi.service.impl;

import com.iase24.crazy_task_tracker_api.adminapi.service.UserService;
import com.iase24.crazy_task_tracker_api.businessapi.dto.ChatMessageDto;
import com.iase24.crazy_task_tracker_api.businessapi.dto.request.MessageSendRequest;
import com.iase24.crazy_task_tracker_api.businessapi.repository.ChatMessageRepository;
import com.iase24.crazy_task_tracker_api.businessapi.repository.ChatRoomMemberRepository;
import com.iase24.crazy_task_tracker_api.businessapi.repository.ChatRoomRepository;
import com.iase24.crazy_task_tracker_api.businessapi.service.ChatService;
import com.iase24.crazy_task_tracker_api.dto.UserDetailsDto;
import com.iase24.crazy_task_tracker_api.entity.ChatMessage;
import com.iase24.crazy_task_tracker_api.entity.ChatRoom;
import com.iase24.crazy_task_tracker_api.entity.ChatRoomMember;
import com.iase24.crazy_task_tracker_api.entity.enumerate.ChatRoomType;
import com.iase24.crazy_task_tracker_api.mapper.ChatMessageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatServiceImpl implements ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final ChatMessageMapper chatMessageMapper;
    private final UserService userService;

    @Override
    public String answerMessageDump(String data) {
        var CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuwxyz0123456789";
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < data.length(); i++) {
            int index = ThreadLocalRandom.current().nextInt(CHARACTERS.length());
            result.append(CHARACTERS.charAt(index));
        }

        return result.toString();
    }

    @Override
    @Transactional
    public ChatMessageDto sendMessage(MessageSendRequest request, UUID senderId) throws AccessDeniedException {
        UUID roomId = request.getRoomId();

        if (!chatRoomMemberRepository.existsByRoomIdAndUserId(roomId, senderId)) {
            throw new AccessDeniedException("User " + senderId + " is not a member of room " + roomId);
        }

        ChatRoom room = chatRoomRepository.getReferenceById(roomId);
        ChatMessage message = new ChatMessage();
        message.setRoom(room);
        message.setSenderId(senderId);
        message.setContent(request.getContent());

        ChatMessage savedMessage = chatMessageRepository.save(message);
        log.info("Message from user {} saved to room {}", senderId, roomId);

        ChatMessageDto dto = chatMessageMapper.toDto(savedMessage);

        enrichMessageDtos(List.of(dto));


        return dto;
    }

    @Override
    public Page<ChatMessageDto> getMessagesForRoom(UUID roomId, UUID currentUserId, Pageable pageable) throws AccessDeniedException {
        if (!chatRoomMemberRepository.existsByRoomIdAndUserId(roomId, currentUserId)) {
            throw new AccessDeniedException("User " + currentUserId + " does not have access to room " + roomId);
        }

        Page<ChatMessage> messagePage = chatMessageRepository.findByRoomId(roomId, pageable);

        Page<ChatMessageDto> dtoPage = messagePage.map(chatMessageMapper::toDto);

        enrichMessageDtos(dtoPage.getContent());

        return dtoPage;
    }

    @Override
    public UUID findOrCreatePrivateRoom(UUID userFirstId, UUID userSecondId) {
        if (userFirstId.equals(userSecondId)) {
            throw new IllegalArgumentException("Cannot create a private chat with yourself.");
        }


        return chatRoomRepository.findPrivateRoomBetweenUsers(userFirstId, userSecondId)
                .map(ChatRoom::getId)
                .orElseGet(() -> createPrivateRoom(userFirstId, userSecondId));
    }

    private UUID createPrivateRoom(UUID user1Id, UUID user2Id) {
        log.info("Creating a new private room for users {} and {}", user1Id, user2Id);
        ChatRoom room = new ChatRoom();
        room.setType(ChatRoomType.PRIVATE);

        ChatRoomMember member1 = new ChatRoomMember();
        member1.setUserId(user1Id);
        room.addMember(member1);

        ChatRoomMember member2 = new ChatRoomMember();
        member2.setUserId(user2Id);
        room.addMember(member2);

        return chatRoomRepository.save(room).getId();
    }

    /**
     * Эффективно обогащает список DTO сообщений данными об отправителях (имя, аватар).
     *
     * @param messages Список DTO для обогащения.
     */
    private void enrichMessageDtos(List<ChatMessageDto> messages) {
        if (messages.isEmpty()) {
            return;
        }

        Set<UUID> senderIds = messages.stream()
                .map(ChatMessageDto::getSenderId)
                .collect(Collectors.toSet());

        Map<UUID, UserDetailsDto> userDetailsMap = userService.getUsersByIds(senderIds);

        messages.forEach(dto -> {
            userDetailsMap.getOrDefault(dto.getSenderId(), new UserDetailsDto(dto.getSenderId(), "Неизвестный пользователь", null));
        });
    }
}
