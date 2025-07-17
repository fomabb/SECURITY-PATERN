package com.iase24.crazy_task_tracker_api.businessapi.service;

import com.iase24.crazy_task_tracker_api.businessapi.dto.response.MovieResponse;
import com.iase24.crazy_task_tracker_api.util.pageable.PageableResponse;
import org.springframework.data.domain.Pageable;

public interface MovieServiceOperationEs {


    PageableResponse<MovieResponse> searchNativeQuery(String query, Pageable pageable);
}
