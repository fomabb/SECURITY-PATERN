package com.iase24.crazy_task_tracker_api.businessapi.service;

import com.iase24.crazy_task_tracker_api.businessapi.dto.GroupChatDetailsDto;
import com.iase24.crazy_task_tracker_api.businessapi.dto.GroupChatListDto;
import com.iase24.crazy_task_tracker_api.businessapi.dto.request.GroupChatCreateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface GroupChatService {
    Page<GroupChatListDto> getAllGroups(Pageable pageable);

    GroupChatDetailsDto getGroupById(UUID groupId);

    GroupChatDetailsDto createGroup(GroupChatCreateRequest request, UUID creatorId);

    void deleteGroup(UUID groupId, UUID currentUserId);

    void joinGroup(UUID groupId, UUID userId);

    void leaveGroup(UUID groupId, UUID userId);
}
