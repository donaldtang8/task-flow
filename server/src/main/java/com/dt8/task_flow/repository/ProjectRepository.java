package com.dt8.task_flow.repository;

import com.dt8.task_flow.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByOwnerId(long ownerId);
}
