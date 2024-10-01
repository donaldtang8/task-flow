package com.dt8.task_flow.mapper;

import com.dt8.task_flow.entity.Task;
import com.dt8.task_flow.entity.TaskStatus;
import com.dt8.task_flow.entity.User;
import com.dt8.task_flow.rest.dto.*;
import com.dt8.task_flow.security.CustomUserDetails;
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

    @Autowired
    public TaskMapperImpl(UserMapper userMapper, ProjectMapper projectMapper) {
        this.userMapper = userMapper;
        this.projectMapper = projectMapper;
    }

    @Override
    public Task toTask(CreateTaskRequest createTaskRequest, @AuthenticationPrincipal CustomUserDetails currentUser, UserService userService) {
        if (createTaskRequest == null) {
            return null;
        }
        Optional<User> userOptional = userService.getUserById(currentUser.getId());
        Task task = new Task(createTaskRequest.getTitle(), createTaskRequest.getDescription());
        if (userOptional.isEmpty()) {
            throw new RuntimeException("No user found");
        }
        task.setCreatedBy(userOptional.get());
        return task;
    }

    @Override
    public Task toTask(UpdateTaskRequest updateTaskRequest, UserService userService) {
        if (updateTaskRequest == null) {
            return null;
        }
        Task task = new Task(updateTaskRequest.getTitle(), updateTaskRequest.getDescription());
        task.setStatus(updateTaskRequest.getStatus());
        task.setProject(updateTaskRequest.getProject());
        task.setTargetDate(updateTaskRequest.getTargetDate());
        task.setUpdatedAt(updateTaskRequest.getUpdatedAt());
        Optional<User> assigneeOptional = userService.getUserById(updateTaskRequest.getAssignee());
        Optional<User> assignerOptional = userService.getUserById(updateTaskRequest.getAssigner());
        Optional<User> createdByOptional = userService.getUserById(updateTaskRequest.getCreatedBy());
        if (assigneeOptional.isEmpty() || assignerOptional.isEmpty() || createdByOptional.isEmpty()) {
            throw new RuntimeException("No user found");
        }
        task.setAssignee(assigneeOptional.get());
        task.setAssigner(assignerOptional.get());
        task.setCreatedBy(createdByOptional.get());
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
