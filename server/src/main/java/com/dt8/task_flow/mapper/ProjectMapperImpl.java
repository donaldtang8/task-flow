package com.dt8.task_flow.mapper;

import com.dt8.task_flow.entity.Project;
import com.dt8.task_flow.entity.ProjectStatus;
import com.dt8.task_flow.entity.User;
import com.dt8.task_flow.rest.dto.*;
import com.dt8.task_flow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectMapperImpl implements ProjectMapper {
    private final UserMapper userMapper;
    private final UserService userService;
    private final TaskMapper taskMapper;

    @Autowired
    public ProjectMapperImpl(UserMapper userMapper, UserService userService, TaskMapper taskMapper) {

        this.userMapper = userMapper;
        this.userService = userService;
        this.taskMapper = taskMapper;
    }

    @Override
    public Project toProject(CreateProjectRequest createProjectRequest) {
        if (createProjectRequest == null) {
            return null;
        }
        Project project = new Project(createProjectRequest.getTitle(), createProjectRequest.getDescription());
        User owner = userService.getUserById(userService.getCurrentUser().getId()).get();
        project.setOwner(owner);
        ProjectStatus status = createProjectRequest.getStatus();
        if (status == null) {
            project.setStatus(ProjectStatus.NONE);
        } else {
            project.setStatus(createProjectRequest.getStatus());
        }
        project.setTargetDate(createProjectRequest.getTargetDate());
        return project;
    }

    @Override
    public Project toProject(UpdateProjectRequest updateProjectRequest) {
        if (updateProjectRequest == null) {
            return null;
        }
        Project project = new Project(updateProjectRequest.getTitle(), updateProjectRequest.getDescription());
        project.setStatus(updateProjectRequest.getStatus());
        Optional<User> ownerOptional = userService.getUserById(updateProjectRequest.getOwnerId());
        if (ownerOptional.isEmpty()) {
            throw new RuntimeException("No user found");
        }
        project.setOwner(ownerOptional.get());
        return project;
    }

    @Override
    public ProjectDto toProjectDto(Project project) {
        if (project == null) {
            return null;
        }
        long id = project.getId();
        String title = project.getTitle();
        String description = project.getDescription();
        ProjectStatus status = project.getStatus();
        LocalDateTime createdAt = project.getCreatedAt();
        LocalDateTime updatedAt = project.getUpdatedAt();
        UserDto owner = userMapper.toUserDto(project.getOwner());
        List<UserDto> users;
        if (project.getUsers() == null) {
            users = new ArrayList<>();
        } else {
            users = userMapper.toUserDto(project.getUsers());
        }
        List<TaskDto> tasks;
        if (project.getTasks() == null) {
            tasks = new ArrayList<>();
        } else {
            tasks = taskMapper.toTaskDto(project.getTasks());
        }

        return new ProjectDto(id, title, description, status, createdAt, updatedAt, owner, users, tasks);
    }
}
