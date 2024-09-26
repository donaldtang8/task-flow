package com.dt8.task_flow.rest.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.dt8.task_flow.entity.ProjectStatus;
import com.dt8.task_flow.entity.User;

public record ProjectDto (long id, String title, String description, ProjectStatus status, LocalDateTime createdAt, User owner, List<User> users) {
}

