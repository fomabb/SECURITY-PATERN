package com.iase24.crazy_task_tracker_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "archive_deleted_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArchiveDeletedUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message")
    private String message;

    @Column(name = "add_date_time_to_archive")
    private String addDateTimeToArchive;
}
