package com.iase24.crazy_task_tracker_api.businessapi.service.impl;

import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import co.elastic.clients.elasticsearch._types.query_dsl.TextQueryType;
import com.iase24.crazy_task_tracker_api.businessapi.document.MovieDoc;
import com.iase24.crazy_task_tracker_api.businessapi.dto.response.MovieResponse;
import com.iase24.crazy_task_tracker_api.businessapi.service.MovieServiceOperationEs;
import com.iase24.crazy_task_tracker_api.util.pageable.PageableResponse;
import com.iase24.crazy_task_tracker_api.util.pageable.PageableResponseUtil;
import com.iase24.crazy_task_tracker_api.util.response.MovieResponseEs;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieServiceOperationEsImpl implements MovieServiceOperationEs {

    private final ElasticsearchOperations operations;
    private final MovieResponseEs movieResponseEs;
    private final PageableResponseUtil pageableResponseUtil;

    @Override
    @SneakyThrows
    public PageableResponse<MovieResponse> searchNativeQuery(String query, Pageable pageable) {
        SearchHits<MovieDoc> searchHits = getSearchHits(query, pageable);


        Page<MovieDoc> page = new PageImpl<>(
                searchHits.stream().map(SearchHit::getContent).toList(), pageable, searchHits.getTotalHits()
        );

        List<MovieResponse> moviesFromDb = movieResponseEs.getMovieResponsesFromDocument(page);

        return pageableResponseUtil.buildPageableResponse(moviesFromDb, page, new PageableResponse<>());
    }

    private SearchHits<MovieDoc> getSearchHits(String query, Pageable pageable) {
        Query nativeQueryBuilder = NativeQuery.builder()
                .withQuery(q -> q
                        .bool(b -> b.should(s -> s
                                                .multiMatch(m -> m
                                                        .query(query)
                                                        .fields(List.of("movie^4", "overview^3"))
                                                        .fuzziness("AUTO")
                                                        .type(TextQueryType.BestFields)
                                                        .operator(Operator.Or)
                                                )
                                        )
                                        .should(s -> s
                                                .matchPhrase(mp -> mp
                                                        .field("movie")
                                                        .query(query)
                                                        .boost(3F)
                                                )
                                        )
                                        .should(s -> s
                                                .matchPhrase(mp -> mp
                                                        .field("overview")
                                                        .query(query)
                                                        .boost(2F)
                                                )
                                        )
                                        .minimumShouldMatch("1")
                        )

                )
                .withPageable(pageable)
                .build();

        return operations.search(nativeQueryBuilder, MovieDoc.class);
    }
}
