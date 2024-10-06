package com.dt8.task_flow.mapper;

import com.dt8.task_flow.entity.*;
import com.dt8.task_flow.rest.dto.*;
import com.dt8.task_flow.security.CustomUserDetails;
import com.dt8.task_flow.service.ProjectService;
import com.dt8.task_flow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskMapperImpl implements TaskMapper {
    private final UserMapper userMapper;
    private final UserService userService;
    private final ProjectService projectService;

    @Autowired
    public TaskMapperImpl(UserMapper userMapper, UserService userService, ProjectService projectService) {
        this.userMapper = userMapper;
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
        TaskProjectDto project = toTaskProjectDto(task.getProject());
        LocalDate targetDate = task.getTargetDate();
        LocalDateTime createdAt = task.getCreatedAt();
        LocalDateTime updatedAt = task.getUpdatedAt();

        return new TaskDto(id, title, description, status, assignee, assigner, project, createdBy, targetDate, createdAt, updatedAt);
    }

    @Override
    public List<TaskDto> toTaskDto(List<Task> tasks) {
        return tasks
                .stream()
                .map(this::toTaskDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaskProjectDto toTaskProjectDto(Project project) {
        long id = project.getId();
        String title = project.getTitle();
        String description = project.getDescription();
        ProjectStatus status = project.getStatus();
        LocalDateTime createdAt = project.getCreatedAt();
        LocalDateTime updatedAt = project.getUpdatedAt();
        UserDto owner = userMapper.toUserDto(project.getOwner());
        return new TaskProjectDto(id, title, description, status, createdAt, updatedAt, owner);
    }
}
