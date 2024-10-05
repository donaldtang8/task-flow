package com.dt8.task_flow.service;

import com.dt8.task_flow.entity.Project;
import com.dt8.task_flow.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    Optional<Task> getTaskById(long taskId);

    List<Task> getTasksByProjectId(long projectId);

    List<Task> getTasksByUserId(long userId);

    Task createTask(Task task);

    Project addTaskToProjectById(long projectId, long taskId);

    Project removeTaskFromProjectById(long projectId, long taskId);

    Task updateTaskById(long taskId, Task task);

    void deleteTaskById(long taskId);

    boolean validateTaskById(long taskId);

    boolean validateTaskPermissionById(long taskId);
}
