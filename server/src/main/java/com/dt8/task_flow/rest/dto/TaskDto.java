package com.dt8.task_flow.rest.dto;

import com.dt8.task_flow.entity.TaskStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TaskDto (long id, String title, String description, TaskStatus status, UserDto assignee, UserDto assigner, TaskProjectDto project, UserDto createdBy, LocalDate targetDate, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
