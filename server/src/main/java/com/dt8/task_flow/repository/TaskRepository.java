package com.dt8.task_flow.repository;

import com.dt8.task_flow.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByProjectId(long projectId);

    List<Task> findByAssigneeId(long userId);
}
