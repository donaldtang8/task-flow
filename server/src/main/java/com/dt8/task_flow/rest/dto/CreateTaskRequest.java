package com.dt8.task_flow.rest.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateTaskRequest {
    @Size(min=3, message="Title must be at least 3 characters long")
    @Size(max=50, message="Title cannot be more than 50 characters long")
    private String title;

    @Size(min=3, message="Title must be at least 3 characters long")
    @Size(max=200, message="Title cannot be more than 200 characters long")
    private String description;

    @NotNull
    private long projectId;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public long getProjectId() {
        return projectId;
    }
}
