package com.iase24.crazy_task_tracker_api.businessapi.service.impl;

import com.iase24.crazy_task_tracker_api.businessapi.dto.GroupChatDetailsDto;
import com.iase24.crazy_task_tracker_api.businessapi.dto.GroupChatListDto;
import com.iase24.crazy_task_tracker_api.businessapi.dto.request.GroupChatCreateRequest;
import com.iase24.crazy_task_tracker_api.businessapi.repository.GroupChatMemberRepository;
import com.iase24.crazy_task_tracker_api.businessapi.repository.GroupChatRepository;
import com.iase24.crazy_task_tracker_api.businessapi.service.GroupChatService;
import com.iase24.crazy_task_tracker_api.entity.ChatRoom;
import com.iase24.crazy_task_tracker_api.entity.ChatRoomMember;
import com.iase24.crazy_task_tracker_api.entity.GroupChat;
import com.iase24.crazy_task_tracker_api.entity.GroupChatMember;
import com.iase24.crazy_task_tracker_api.entity.enumerate.ChatRoomType;
import com.iase24.crazy_task_tracker_api.entity.enumerate.GroupChatRole;
import com.iase24.crazy_task_tracker_api.entity.enumerate.GroupChatType;
import com.iase24.crazy_task_tracker_api.mapper.GroupChatMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupChatServiceImpl implements GroupChatService {

    private final GroupChatRepository groupChatRepository;
    private final GroupChatMemberRepository groupChatMemberRepository;
    private final GroupChatMapper groupChatMapper;

    @Override
    public Page<GroupChatListDto> getAllGroups(Pageable pageable) {
        return groupChatRepository.findAll(pageable).map(groupChatMapper::toListDto);
    }

    @Override
    public GroupChatDetailsDto getGroupById(UUID groupId) {
        return groupChatRepository.findById(groupId)
                .map(groupChatMapper::toDetailsDto)
                .orElseThrow(() -> new EntityNotFoundException("Group with id " + groupId + " not found"));
    }

    @Override
    @Transactional
    public GroupChatDetailsDto createGroup(GroupChatCreateRequest request, UUID creatorId) {
        GroupChat groupChat = groupChatMapper.toEntity(request);
        groupChat.setCreatedByUserId(creatorId);

        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setType(ChatRoomType.GROUP);
        groupChat.setChatRoom(chatRoom);

        addMemberToGroup(groupChat, creatorId, GroupChatRole.OWNER);

        GroupChat savedGroup = groupChatRepository.save(groupChat);

        return groupChatMapper.toDetailsDto(savedGroup);
    }

    @Override
    @Transactional
    public void deleteGroup(UUID groupId, UUID currentUserId) {
        GroupChat groupChat = findGroupByIdOrThrow(groupId);
        if (!groupChat.getCreatedByUserId().equals(currentUserId)) {
            throw new AccessDeniedException("Only the owner can delete the group.");
        }
        groupChatRepository.delete(groupChat);
    }

    @Override
    @Transactional
    public void joinGroup(UUID groupId, UUID userId) {
        GroupChat groupChat = findGroupByIdOrThrow(groupId);

        if (groupChatMemberRepository.existsByGroupChatIdAndUserId(groupId, userId)) {
            return;
        }

        if (groupChat.getType() == GroupChatType.PRIVATE) {
            throw new AccessDeniedException("Cannot join a private group directly.");
        }

        addMemberToGroup(groupChat, userId, GroupChatRole.MEMBER);
        groupChatRepository.save(groupChat);
    }

    @Override
    @Transactional
    public void leaveGroup(UUID groupId, UUID userId) {
        GroupChatMember member = groupChatMemberRepository.findByGroupChatIdAndUserId(groupId, userId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found in the group."));

        if (member.getRole() == GroupChatRole.OWNER) {
            throw new IllegalStateException("Owner cannot leave the group. Delete it or transfer ownership.");
        }

        GroupChat groupChat = member.getGroupChat();
        groupChat.getMembers().remove(member);
        groupChat.getChatRoom().getMembers().removeIf(m -> m.getUserId().equals(userId));

        groupChatRepository.save(groupChat);
    }

    private GroupChat findGroupByIdOrThrow(UUID groupId) {
        return groupChatRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group with id " + groupId + " not found"));
    }

    private void addMemberToGroup(GroupChat group, UUID userId, GroupChatRole role) {
        GroupChatMember groupMember = new GroupChatMember();
        groupMember.setUserId(userId);
        groupMember.setRole(role);
        group.addMember(groupMember);

        ChatRoomMember roomMember = new ChatRoomMember();
        roomMember.setUserId(userId);
        group.getChatRoom().addMember(roomMember);
    }
}
