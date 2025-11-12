package com.iase24.crazy_task_tracker_api.mapper;

import com.iase24.crazy_task_tracker_api.businessapi.dto.ChatMessageDto;
import com.iase24.crazy_task_tracker_api.entity.ChatMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChatMessageMapper {

    @Mapping(target = "roomId", source = "room.id")
    @Mapping(target = "senderName", ignore = true)
    @Mapping(target = "senderAvatarUrl", ignore = true)
    ChatMessageDto toDto(ChatMessage entity);
}
