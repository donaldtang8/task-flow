package com.dt8.task_flow.rest.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.dt8.task_flow.entity.ProjectStatus;

public record ProjectDto (long id, String title, String description, ProjectStatus status, LocalDateTime createdAt, LocalDateTime updatedAt, UserDto owner, List<UserDto> users, List<TaskDto> tasks) {
}

