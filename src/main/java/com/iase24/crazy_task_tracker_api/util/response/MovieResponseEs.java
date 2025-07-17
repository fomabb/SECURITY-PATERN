package com.iase24.crazy_task_tracker_api.util.response;

import com.iase24.crazy_task_tracker_api.businessapi.document.MovieDoc;
import com.iase24.crazy_task_tracker_api.businessapi.dto.response.MovieResponse;
import com.iase24.crazy_task_tracker_api.businessapi.repository.MovieRepository;
import com.iase24.crazy_task_tracker_api.mapper.MovieMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Comparator.comparingInt;

@Component
@RequiredArgsConstructor
public class MovieResponseEs {

    private final MovieMapper movieMapper;
    private final MovieRepository movieRepository;

    public List<MovieResponse> getMovieResponsesFromDocument(Page<MovieDoc> page) {

        Map<Long, Integer> idsMap = new LinkedHashMap<>();

        List<MovieDoc> movieDocs = page.getContent();

        for (int i = 0; i < movieDocs.size(); i++) {
            idsMap.put(movieDocs.get(i).getId(), i);
        }
        Set<Long> ids = idsMap.keySet();

        return movieMapper.movieListEntityToMovieListResponseDto(
                movieRepository.findAllById(ids).stream()
                        .sorted(comparingInt(movie -> idsMap.get(movie.getId()))).toList());
    }
}
