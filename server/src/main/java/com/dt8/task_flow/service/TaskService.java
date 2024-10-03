package com.dt8.task_flow.service;

import com.dt8.task_flow.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    Optional<Task> getTaskById(long taskId);

    List<Task> getTasksByProjectId(long projectId);

    List<Task> getTasksByUserId(long userId);

    Task createTask(Task task);

    Task updateTaskById(long taskId, Task task);

    void deleteTaskById(long taskId);

    Task validateAndGetTaskById(long taskId);

    boolean userHasTaskPermission(Task task);
}
