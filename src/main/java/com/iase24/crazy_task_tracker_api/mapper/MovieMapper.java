package com.iase24.crazy_task_tracker_api.mapper;

import com.iase24.crazy_task_tracker_api.businessapi.dto.response.MovieResponse;
import com.iase24.crazy_task_tracker_api.entity.Movie;

import java.util.List;

public interface MovieMapper {

    MovieResponse movieEntityToMovieResponseDto(Movie movie);

    List<MovieResponse> movieListEntityToMovieListResponseDto(List<Movie> movies);
}
