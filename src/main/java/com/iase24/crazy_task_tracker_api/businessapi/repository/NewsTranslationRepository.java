package com.iase24.crazy_task_tracker_api.businessapi.repository;

import com.iase24.crazy_task_tracker_api.entity.NewsTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NewsTranslationRepository extends JpaRepository<NewsTranslation, Long> {

    Optional<NewsTranslation> findByNewsIdAndLanguage(Long newsId, String language);

    List<NewsTranslation> findNewsTranslationByLanguage(String language);

    @Modifying
    @Query(value = """
            INSERT INTO news_translations (news_id, language, title, info)
            SELECT news_id, language, title, info
            FROM pars_language_data
            """, nativeQuery = true)
    void insertNewLanguageToNews();
}
