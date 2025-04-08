package com.iase24.crazy_task_tracker_api.businessapi.controller;

import com.iase24.crazy_task_tracker_api.businessapi.dto.response.MovieResponse;
import com.iase24.crazy_task_tracker_api.businessapi.service.MovieService;
import com.iase24.crazy_task_tracker_api.util.pageable.PageableResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
@RequiredArgsConstructor
@Validated
@Slf4j
@Tag(name = "Управление кинотеатром", description = "'Интерфейс для управления афишей фильмов'")
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/reindex")
    public List<String> reindexAllMovies() {
        movieService.reindexMovies();
        return List.of("Reindex all movie successfully.");
    }

    @GetMapping("/search")
    public ResponseEntity<PageableResponse<MovieResponse>> searchMovies(
            @RequestParam("query") String query,
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        return ResponseEntity.ok(movieService.searchMovies(query, PageRequest.of(page - 1, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> getMovieById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(movieService.getCachedMovies(id));
    }

    @GetMapping("/all")
    public ResponseEntity<PageableResponse<MovieResponse>> getAllMovies(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        return ResponseEntity.ok(movieService.getAllMovies(PageRequest.of(page - 1, size)));
    }

    @GetMapping("/trending")
    public ResponseEntity<MovieResponse> getRandomMovies() {
        return ResponseEntity.ok(movieService.getRandomMovie());
    }

    @GetMapping("/six-trending")
    public ResponseEntity<List<MovieResponse>> getSixTrending() {
        return ResponseEntity.ok(movieService.getSixTrending());
    }
}
