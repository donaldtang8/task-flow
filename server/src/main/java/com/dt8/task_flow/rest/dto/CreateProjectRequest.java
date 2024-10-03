package com.dt8.task_flow.rest.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class CreateProjectRequest {
    @Min(value=3, message="Title must be at least 3 characters long")
    private String title;

    @Min(value=10, message="Description must be at least 10 characters long")
    private String description;

    @NotBlank
    private long ownerId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
