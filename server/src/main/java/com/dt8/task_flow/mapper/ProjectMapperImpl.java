package com.dt8.task_flow.mapper;

import com.dt8.task_flow.entity.Project;
import com.dt8.task_flow.entity.ProjectStatus;
import com.dt8.task_flow.entity.User;
import com.dt8.task_flow.rest.dto.CreateProjectRequest;
import com.dt8.task_flow.rest.dto.ProjectDto;
import com.dt8.task_flow.rest.dto.UpdateProjectRequest;
import com.dt8.task_flow.rest.dto.UserDto;
import com.dt8.task_flow.security.CustomUserDetails;
import com.dt8.task_flow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectMapperImpl implements ProjectMapper {
    private final UserMapper userMapper;

    @Autowired
    public ProjectMapperImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    @Override
    public Project toProject(CreateProjectRequest createProjectRequest, @AuthenticationPrincipal CustomUserDetails currentUser, UserService userService) {
        if (createProjectRequest == null) {
            return null;
        }
        Project project = new Project(createProjectRequest.getTitle(), createProjectRequest.getDescription());
        Optional<User> ownerOptional = userService.getUserById(currentUser.getId());
        if (ownerOptional.isEmpty()) {
            throw new RuntimeException("No user found");
        }
        project.setOwner(ownerOptional.get());
        return project;
    }

    @Override
    public Project toProject(UpdateProjectRequest updateProjectRequest, UserService userService) {
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
        UserDto owner = userMapper.toUserDto(project.getOwner());
        List<UserDto> users = userMapper.toUserDto(project.getUsers());

        return new ProjectDto(id, title, description, status, createdAt, owner, users);
    }
}
