package com.dt8.task_flow.rest.dto;

import com.dt8.task_flow.entity.Project;
import com.dt8.task_flow.entity.TaskStatus;
import com.dt8.task_flow.entity.User;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class UpdateTaskRequest {
    @Size(min=3, message="Title must be at least 3 characters long")
    @Size(max=50, message="Title cannot be more than 50 characters long")
    private String title;

    @Size(min=3, message="Title must be at least 3 characters long")
    @Size(max=20, message="Title cannot be more than 200 characters long")
    private String description;

    @NotNull
    private TaskStatus status;

    @NotNull
    private long assignee;

    @NotNull
    private long assigner;

    @NotNull
    private LocalDateTime targetDate;

    @NotNull
    private long project;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public long getAssignee() {
        return assignee;
    }

    public long getAssigner() {
        return assigner;
    }

    public LocalDateTime getTargetDate() {
        return targetDate;
    }

    public long getProject() {
        return project;
    }
}
