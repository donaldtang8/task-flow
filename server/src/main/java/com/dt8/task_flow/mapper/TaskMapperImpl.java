package com.dt8.task_flow.mapper;

import com.dt8.task_flow.entity.Project;
import com.dt8.task_flow.entity.Task;
import com.dt8.task_flow.entity.TaskStatus;
import com.dt8.task_flow.entity.User;
import com.dt8.task_flow.rest.dto.*;
import com.dt8.task_flow.security.CustomUserDetails;
import com.dt8.task_flow.service.ProjectService;
import com.dt8.task_flow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TaskMapperImpl implements TaskMapper {
    private final UserMapper userMapper;
    private final ProjectMapper projectMapper;
    private final UserService userService;
    private final ProjectService projectService;

    @Autowired
    public TaskMapperImpl(UserMapper userMapper, ProjectMapper projectMapper, UserService userService, ProjectService projectService) {
        this.userMapper = userMapper;
        this.projectMapper = projectMapper;
        this.userService = userService;
        this.projectService = projectService;
    }

    @Override
    public Task toTask(CreateTaskRequest createTaskRequest, @AuthenticationPrincipal CustomUserDetails currentUser) {
        if (createTaskRequest == null) {
            return null;
        }
        Optional<User> userOptional = userService.getUserById(currentUser.getId());
        Optional<Project> projectOptional = projectService.getProjectById(createTaskRequest.getProjectId());
        if (userOptional.isEmpty() || projectOptional.isEmpty()) {
            throw new RuntimeException("No user found");
        }
        Task task = new Task(createTaskRequest.getTitle(), createTaskRequest.getDescription(), projectOptional.get());
        task.setCreatedBy(userOptional.get());
        return task;
    }

    @Override
    public Task toTask(UpdateTaskRequest updateTaskRequest) {
        if (updateTaskRequest == null) {
            return null;
        }
        Optional<Project> projectOptional = projectService.getProjectById(updateTaskRequest.getProject());
        Optional<User> assigneeOptional = userService.getUserById(updateTaskRequest.getAssignee());
        Optional<User> assignerOptional = userService.getUserById(updateTaskRequest.getAssigner());
        if (assigneeOptional.isEmpty() || assignerOptional.isEmpty() || projectOptional.isEmpty()) {
            throw new RuntimeException("Invalid project, assignee, or assigner");
        }
        Task task = new Task(updateTaskRequest.getTitle(), updateTaskRequest.getDescription(), projectOptional.get());
        task.setStatus(updateTaskRequest.getStatus());
        task.setTargetDate(updateTaskRequest.getTargetDate());
        task.setUpdatedAt(LocalDateTime.now());
        task.setAssignee(assigneeOptional.get());
        task.setAssigner(assignerOptional.get());
        return task;
    }

    @Override
    public TaskDto toTaskDto(Task task) {
        if (task == null) {
            return null;
        }
        long id = task.getId();
        String title = task.getTitle();
        String description = task.getDescription();
        TaskStatus status = task.getStatus();
        UserDto assignee = userMapper.toUserDto(task.getAssignee());
        UserDto assigner = userMapper.toUserDto(task.getAssigner());
        UserDto createdBy = userMapper.toUserDto(task.getCreatedBy());
        ProjectDto project = projectMapper.toProjectDto(task.getProject());
        LocalDateTime targetDate = task.getTargetDate();
        LocalDateTime createdAt = task.getCreatedAt();
        LocalDateTime updatedAt = task.getUpdatedAt();

        return new TaskDto(id, title, description, status, assignee, assigner, project, createdBy, targetDate, createdAt, updatedAt);
    }
}
