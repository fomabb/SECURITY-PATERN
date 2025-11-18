package com.iase24.crazy_task_tracker_api.entity;

import com.iase24.crazy_task_tracker_api.entity.enumerate.GroupChatRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "group_chat_members", uniqueConstraints = {
        // Гарантирует, что пользователь может вступить в группу только один раз
        @UniqueConstraint(columnNames = {"group_chat_id", "user_id"})
})
public class GroupChatMember {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_chat_id", nullable = false)
    private GroupChat groupChat;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GroupChatRole role; // OWNER, ADMIN, MEMBER

    @Column(name = "is_club_member", nullable = false)
    private boolean isClubMember = false;

    @CreationTimestamp
    @Column(name = "joined_at", nullable = false, updatable = false)
    private Instant joinedAt;
}
