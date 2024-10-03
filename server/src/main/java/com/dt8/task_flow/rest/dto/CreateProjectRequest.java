package com.dt8.task_flow.rest.dto;

import com.dt8.task_flow.entity.ProjectStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class CreateProjectRequest {
    @Size(min=3, message="Title must be at least 3 characters long")
    private String title;

    @NotBlank
    private String description;

    private ProjectStatus status;

    private LocalDateTime targetDate;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public LocalDateTime getTargetDate() {
        return targetDate;
    }
}
