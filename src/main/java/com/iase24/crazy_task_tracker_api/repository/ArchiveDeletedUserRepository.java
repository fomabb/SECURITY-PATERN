package com.iase24.crazy_task_tracker_api.repository;

import com.iase24.crazy_task_tracker_api.entity.ArchiveDeletedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArchiveDeletedUserRepository extends JpaRepository<ArchiveDeletedUser, Long> {

    @Query("select m FROM ArchiveDeletedUser m WHERE m.message ilike %:text%")
    List<ArchiveDeletedUser> findMessageBySearch(String text);
}
