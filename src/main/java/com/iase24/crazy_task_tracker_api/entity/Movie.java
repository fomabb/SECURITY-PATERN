package com.iase24.crazy_task_tracker_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "movies")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer rating;

    private String movie;

    private Integer year;

    private String country;

    @Column(name = "rating_ball")
    private Float ratingBall;

    @Column(columnDefinition = "TEXT")
    private String overview;

    private String director;

    @Column(name = "screenwriter")
    private List<String> screenwriter;

    @Column(name = "actors")
    private List<String> actors;

    @Column(name = "url_logo")
    private String urlLogo;
}