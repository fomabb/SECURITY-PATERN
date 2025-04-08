package com.iase24.crazy_task_tracker_api.businessapi.service;

import com.iase24.crazy_task_tracker_api.entity.Movie;
import com.iase24.crazy_task_tracker_api.util.pageable.PageableResponse;
import org.springframework.data.domain.Pageable;

public interface MovieService {

    PageableResponse<Movie> searchMovies(String query, Pageable pageable);

    void reindexMovies();
}
