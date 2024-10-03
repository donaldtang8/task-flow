package com.dt8.task_flow.rest.dto;

import com.dt8.task_flow.entity.ProjectStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdateProjectRequest {
    @Size(min=3, message="Title must be at least 3 characters long")
    @Size(max=50, message="Title cannot be more than 50 characters long")
    private String title;

    @Size(min=3, message="Title must be at least 3 characters long")
    @Size(max=200, message="Title cannot be more than 200 characters long")
    private String description;

    @NotBlank
    private ProjectStatus status;

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

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }
}
