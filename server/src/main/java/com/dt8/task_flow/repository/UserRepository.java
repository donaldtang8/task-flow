package com.dt8.task_flow.repository;

import com.dt8.task_flow.entity.Project;
import com.dt8.task_flow.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

//    @Query("SELECT p FROM Project p JOIN p.users u WHERE u.id = :userId")
    @Query(value = "SELECT p FROM Project p WHERE p.id IN (SELECT pr.id FROM Project pr JOIN pr.users u WHERE u.id = :userId GROUP BY pr.status)")
    List<Project> findProjectsByUserId(@Param("userId") Long userId);
}
