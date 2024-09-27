package com.dt8.task_flow.mapper;

import com.dt8.task_flow.entity.Project;
import com.dt8.task_flow.entity.ProjectStatus;
import com.dt8.task_flow.entity.User;
import com.dt8.task_flow.rest.dto.CreateProjectRequest;
import com.dt8.task_flow.rest.dto.ProjectDto;
import com.dt8.task_flow.rest.dto.UpdateProjectRequest;
import com.dt8.task_flow.security.CustomUserDetails;
import com.dt8.task_flow.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProjectMapperImpl implements ProjectMapper {
    @Override
    public Project toProject(CreateProjectRequest createProjectRequest, @AuthenticationPrincipal CustomUserDetails currentUser, UserService userService) {
        if (createProjectRequest == null) {
            return null;
        }
        Project project = new Project(createProjectRequest.getTitle(), createProjectRequest.getDescription());
        User owner = userService.getUserById(currentUser.getId());
        project.setOwner(owner);
        return project;
    }

    @Override
    public Project toProject(UpdateProjectRequest updateProjectRequest, UserService userService) {
        if (updateProjectRequest == null) {
            return null;
        }
        Project project = new Project(updateProjectRequest.getTitle(), updateProjectRequest.getDescription());
        project.setStatus(updateProjectRequest.getStatus());
        project.setOwner(userService.getUserById(updateProjectRequest.getOwnerId()));
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
        User owner = project.getOwner();
        List<User> users = project.getUsers();

        return new ProjectDto(id, title, description, status, createdAt, owner, users);
    }
}
