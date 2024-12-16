package com.iase24.crazy_task_tracker_api.entity;

import com.iase24.crazy_task_tracker_api.security.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private News news;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}

