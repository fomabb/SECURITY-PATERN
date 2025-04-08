package com.iase24.crazy_task_tracker_api.mapper.impl;

import com.iase24.crazy_task_tracker_api.businessapi.dto.response.MovieResponse;
import com.iase24.crazy_task_tracker_api.entity.Movie;
import com.iase24.crazy_task_tracker_api.mapper.MovieMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class MovieMapperImpl implements MovieMapper {
    @Override
    public List<MovieResponse> movieEntityToMovieResponseDto(List<Movie> movies) {
        if (movies.isEmpty()) {
            return Collections.emptyList();
        }
        return movies.stream().map(
                movie -> MovieResponse.builder()
                        .id(movie.getId())
                        .rating(movie.getRating())
                        .movie(movie.getMovie())
                        .year(movie.getYear())
                        .country(movie.getCountry())
                        .ratingBall(movie.getRatingBall())
                        .overview(movie.getOverview())
                        .director(movie.getDirector())
                        .screenwriter(movie.getScreenwriter())
                        .actors(movie.getActors())
                        .urlLogo(movie.getUrlLogo())
                        .build()
        ).toList();
    }
}
