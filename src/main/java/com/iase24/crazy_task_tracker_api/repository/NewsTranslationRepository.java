package com.iase24.crazy_task_tracker_api.repository;

import com.iase24.crazy_task_tracker_api.entity.NewsTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NewsTranslationRepository extends JpaRepository<NewsTranslation, Long> {

    Optional<NewsTranslation> findByNewsIdAndLanguage(Long newsId, String language);

    List<NewsTranslation> findNewsTranslationByLanguage(String language);
}
