package com.iase24.crazy_task_tracker_api.security.repository;

import com.iase24.crazy_task_tracker_api.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String username);

    Optional<User> findByResetToken(String resetToken);

    boolean existsByUsername(String username);

    boolean existsByWorkEmail(String workEmail);

    @Query("SELECT u FROM User u where u.role='ROLE_USER' OR u.role='ROLE_EMPLOYEE'")
    List<User> findAllUserByRoleEmployeeAndRoleUser();
}
