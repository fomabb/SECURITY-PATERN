package com.iase24.crazy_task_tracker_api.repository;

import com.iase24.crazy_task_tracker_api.entity.ParsDataLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParsDataLanguageRepository extends JpaRepository<ParsDataLanguage, Integer> {
}
