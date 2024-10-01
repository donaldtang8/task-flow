package com.dt8.task_flow.rest.dto;

import com.dt8.task_flow.entity.TaskStatus;

import java.time.LocalDateTime;

public record TaskDto (long id, String title, String description, TaskStatus status, UserDto assignee, UserDto assigner, ProjectDto project, UserDto createdBy, LocalDateTime targetDate, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
