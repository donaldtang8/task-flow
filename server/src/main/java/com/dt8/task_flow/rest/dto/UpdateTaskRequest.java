package com.dt8.task_flow.rest.dto;

import com.dt8.task_flow.entity.Project;
import com.dt8.task_flow.entity.TaskStatus;
import com.dt8.task_flow.entity.User;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class UpdateTaskRequest {
    @NotBlank
    @Min(value=3, message="Title must be at least 3 characters long")
    private String title;

    @Min(value=10, message="Description must be at least 10 characters long")
    private String description;

    @NotBlank
    private TaskStatus status;

    @NotBlank
    private User assignee;

    @NotBlank
    private User assigner;

    @NotBlank
    private LocalDateTime targetDate;

    @NotBlank
    private Project project;

    @NotBlank
    private LocalDateTime updatedAt;

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

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public User getAssigner() {
        return assigner;
    }

    public void setAssigner(User assigner) {
        this.assigner = assigner;
    }

    public LocalDateTime getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(LocalDateTime targetDate) {
        this.targetDate = targetDate;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
