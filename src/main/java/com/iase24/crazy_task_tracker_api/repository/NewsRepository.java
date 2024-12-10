package com.iase24.crazy_task_tracker_api.repository;

import com.iase24.crazy_task_tracker_api.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
}
