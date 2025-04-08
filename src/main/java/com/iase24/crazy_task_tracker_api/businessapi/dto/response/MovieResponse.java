package com.iase24.crazy_task_tracker_api.businessapi.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MovieResponse {

    private Long id;

    private Integer rating;

    private String movie;

    private Integer year;

    private String country;

    private Float ratingBall;

    private String overview;

    private String director;

    private List<String> screenwriter;

    private List<String> actors;

    private String urlLogo;
}
