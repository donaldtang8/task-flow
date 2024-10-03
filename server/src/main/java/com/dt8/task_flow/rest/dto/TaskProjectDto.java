package com.dt8.task_flow.rest.dto;

import com.dt8.task_flow.entity.ProjectStatus;

import java.time.LocalDateTime;

public record TaskProjectDto(long id, String title, String description, ProjectStatus status, LocalDateTime createdAt, LocalDateTime updatedAt, UserDto owner) {
}
