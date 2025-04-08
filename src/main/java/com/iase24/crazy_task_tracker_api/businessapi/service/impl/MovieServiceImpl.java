package com.iase24.crazy_task_tracker_api.businessapi.service.impl;

import com.iase24.crazy_task_tracker_api.businessapi.document.MovieDoc;
import com.iase24.crazy_task_tracker_api.businessapi.dto.response.MovieResponse;
import com.iase24.crazy_task_tracker_api.businessapi.repository.MovieRepository;
import com.iase24.crazy_task_tracker_api.businessapi.repository.MovieSearchRepository;
import com.iase24.crazy_task_tracker_api.businessapi.service.MovieService;
import com.iase24.crazy_task_tracker_api.entity.Movie;
import com.iase24.crazy_task_tracker_api.mapper.MovieMapper;
import com.iase24.crazy_task_tracker_api.util.pageable.PageableResponse;
import com.iase24.crazy_task_tracker_api.util.pageable.PageableResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Comparator.comparingInt;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieSearchRepository movieSearchRepository;
    private final PageableResponseUtil pageableResponseUtil;
    private final MovieMapper movieMapper;

    @Override
    @Scheduled(fixedRate = 3600000) // Каждые 1 час
    public void reindexMovies() {
        List<Movie> movies = movieRepository.findAll();
        movieSearchRepository.saveAll(
                movies.stream().map(
                        movie -> MovieDoc.builder()
                                .id(movie.getId())
                                .movie(movie.getMovie())
                                .overview(movie.getMovie())
                                .build()
                ).toList()
        );
    }

    @Override
    public PageableResponse<MovieResponse> searchMovies(String query, Pageable pageable) {
        Page<MovieDoc> searchResult = movieSearchRepository.searchByQuery(query, pageable);
        Map<Long, Integer> idsMap = new LinkedHashMap<>();
        List<MovieDoc> movieDocs = searchResult.getContent();
        for (int i = 0; i < movieDocs.size(); i++) {
            idsMap.put(movieDocs.get(i).getId(), i);
        }
        Set<Long> ids = idsMap.keySet();
        List<MovieResponse> moviesFromDb =
                movieMapper.movieEntityToMovieResponseDto(
                        movieRepository.findAllById(ids).stream()
                                .sorted(comparingInt(movie -> idsMap.get(movie.getId()))).toList());
        return pageableResponseUtil.buildPageableResponse(moviesFromDb, searchResult, new PageableResponse<>());
    }
}
