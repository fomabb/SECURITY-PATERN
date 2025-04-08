package com.iase24.crazy_task_tracker_api.businessapi.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iase24.crazy_task_tracker_api.businessapi.document.MovieDoc;
import com.iase24.crazy_task_tracker_api.businessapi.dto.response.MovieResponse;
import com.iase24.crazy_task_tracker_api.businessapi.repository.MovieRepository;
import com.iase24.crazy_task_tracker_api.businessapi.repository.MovieSearchRepository;
import com.iase24.crazy_task_tracker_api.businessapi.service.MovieService;
import com.iase24.crazy_task_tracker_api.entity.Movie;
import com.iase24.crazy_task_tracker_api.mapper.MovieMapper;
import com.iase24.crazy_task_tracker_api.util.pageable.PageableResponse;
import com.iase24.crazy_task_tracker_api.util.pageable.PageableResponseUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import static com.iase24.crazy_task_tracker_api.config.JedisConfig.jedisPool;
import static java.util.Comparator.comparingInt;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieSearchRepository movieSearchRepository;
    private final PageableResponseUtil pageableResponseUtil;
    private final MovieMapper movieMapper;
    private final ObjectMapper objectMapper;

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
                movieMapper.movieListEntityToMovieListResponseDto(
                        movieRepository.findAllById(ids).stream()
                                .sorted(comparingInt(movie -> idsMap.get(movie.getId()))).toList());
        return pageableResponseUtil.buildPageableResponse(moviesFromDb, searchResult, new PageableResponse<>());
    }

    @Override
    public MovieResponse getMovieById(Long id) {
        return movieMapper.movieEntityToMovieResponseDto(movieRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Movie with ID: %s not found", id))));
    }

    @Override
    public MovieResponse getCachedMovies(Long id) {
        try (Jedis jedis = jedisPool.getResource()) {
            String key = String.format("movie:%s", id);
            String raw = jedis.get(key);
            if (raw != null) {
                return objectMapper.readValue(raw, MovieResponse.class);
            }
            var movie = getMovieById(id);
            if (movie == null) {
                return null;
            }
            jedis.set(key, objectMapper.writeValueAsString(movie));
            return movie;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public MovieResponse getRandomMovie() {
        long count = movieRepository.count();
        long movieNum = new Random().nextLong(1, count);
        return getCachedMovies(movieNum);
    }

    @Override
    public PageableResponse<MovieResponse> getAllMovies(Pageable pageable) {
        Page<Movie> moviePage = movieRepository.findAll(pageable);
        List<MovieResponse> movieResponses = movieMapper.movieListEntityToMovieListResponseDto(moviePage.getContent());
        return pageableResponseUtil.buildPageableResponse(movieResponses, moviePage, new PageableResponse<>());
    }


    @Override
    public List<MovieResponse> getSixTrending() {
        float ratingNumber = 8.7f;
        List<Movie> allMovies = movieRepository.findMoviesByRatingBallLessThanRatingNumber(ratingNumber);

        Collections.shuffle(allMovies);
        return movieMapper.movieListEntityToMovieListResponseDto(new ArrayList<>(allMovies.subList(0, Math.min(6, allMovies.size()))));
    }

    @Override
    public List<MovieResponse> getCachedSixTrending() {
        try (Jedis jedis = jedisPool.getResource()) {
            String key = "movies_trending";
            String raw = jedis.get(key);
            if (raw != null) {
                return objectMapper.readValue(raw, new TypeReference<>() {
                });
            }
            List<MovieResponse> movies = getSixTrending();
            if (movies == null) {
                return Collections.emptyList();
            }
            jedis.set(key, objectMapper.writeValueAsString(movies));
            return movies;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}