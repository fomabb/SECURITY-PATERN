package com.iase24.crazy_task_tracker_api.businessapi.repository;

import com.iase24.crazy_task_tracker_api.entity.NewsDocumentSearch;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsSearchRepository extends ElasticsearchRepository<NewsDocumentSearch, Long> {

    @Query("""
            {
                            "bool": {
                                "must": [
                                    {"match": {"language": "?0"}},
                                    {"multi_match": {
                                        "query": "?1",
                                        "fields": ["title^2", "info"],
                                        "fuzziness": "AUTO"
                                    }}
                                ]
                            }
                        }
            """)
    List<NewsDocumentSearch> findByLanguageAndTitleContainingOrInfoContaining(String language, String title, String info);
}
