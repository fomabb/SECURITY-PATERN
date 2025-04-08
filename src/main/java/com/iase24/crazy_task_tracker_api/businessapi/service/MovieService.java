package com.iase24.crazy_task_tracker_api.businessapi.service;

import com.iase24.crazy_task_tracker_api.businessapi.dto.response.MovieResponse;
import com.iase24.crazy_task_tracker_api.util.pageable.PageableResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MovieService {

    void reindexMovies();

    PageableResponse<MovieResponse> searchMovies(String query, Pageable pageable);

    MovieResponse getMovieById(Long id);

    MovieResponse getCachedMovies(Long id);

    MovieResponse getRandomMovie();

    PageableResponse<MovieResponse> getAllMovies(Pageable pageable);

    List<MovieResponse> getSixTrending();
}
