package com.iase24.crazy_task_tracker_api.businessapi.repository;

import com.iase24.crazy_task_tracker_api.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
}
