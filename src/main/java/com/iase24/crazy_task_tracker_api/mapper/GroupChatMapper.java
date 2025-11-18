package com.iase24.crazy_task_tracker_api.mapper;

import com.iase24.crazy_task_tracker_api.businessapi.dto.GroupChatDetailsDto;
import com.iase24.crazy_task_tracker_api.businessapi.dto.GroupChatListDto;
import com.iase24.crazy_task_tracker_api.businessapi.dto.request.GroupChatCreateRequest;
import com.iase24.crazy_task_tracker_api.entity.GroupChat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GroupChatMapper {

    GroupChat toEntity(GroupChatCreateRequest request);

    @Mapping(target = "chatRoomId", source = "chatRoom.id")
    GroupChatDetailsDto toDetailsDto(GroupChat entity);

    @Mapping(
            target = "memberCount",
            expression = "java(entity.getMembers() != null ? entity.getMembers().size() : 0)"
    )
    GroupChatListDto toListDto(GroupChat entity);
}
