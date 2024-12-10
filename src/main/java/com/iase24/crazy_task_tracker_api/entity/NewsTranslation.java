package com.iase24.crazy_task_tracker_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "news_translations")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsTranslation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "news_id", nullable = false)
    private News news;

    @Column(name = "language")
    private String language;

    @Column(name = "title")
    private String title;

    @Column(name = "info")
    private String info;
}
