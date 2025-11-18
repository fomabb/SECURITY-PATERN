package com.iase24.crazy_task_tracker_api.entity;

import com.iase24.crazy_task_tracker_api.entity.enumerate.GroupChatType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "group_chats")
public class GroupChat {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "cover_url")
    private String coverUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GroupChatType type; // OPEN, BY_REQUEST, PRIVATE

    @Column(name = "is_club_group", nullable = false)
    private boolean isClubGroup = false;

    @Column(name = "club_id")
    private UUID clubId;

    @Column(name = "created_by_user_id", nullable = false)
    private UUID createdByUserId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    // Связь с участниками группы
    @OneToMany(mappedBy = "groupChat", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<GroupChatMember> members = new ArrayList<>();

    // Связь с чат-комнатой (один-к-одному)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "chat_room_id", referencedColumnName = "id")
    private ChatRoom chatRoom;

    // Вспомогательный метод для добавления участника
    public void addMember(GroupChatMember member) {
        members.add(member);
        member.setGroupChat(this);
    }
}
