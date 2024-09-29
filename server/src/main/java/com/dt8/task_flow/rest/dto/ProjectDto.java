package com.dt8.task_flow.rest.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.dt8.task_flow.entity.ProjectStatus;

public record ProjectDto (long id, String title, String description, ProjectStatus status, LocalDateTime createdAt, UserDto owner, List<UserDto> users) {
}

