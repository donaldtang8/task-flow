package com.dt8.task_flow.rest;

import com.dt8.task_flow.entity.Project;
import com.dt8.task_flow.entity.User;
import com.dt8.task_flow.mapper.ProjectMapper;
import com.dt8.task_flow.rest.dto.CreateProjectRequest;
import com.dt8.task_flow.rest.dto.ProjectDto;
import com.dt8.task_flow.rest.dto.UpdateProjectRequest;
import com.dt8.task_flow.security.CustomUserDetails;
import com.dt8.task_flow.service.ProjectService;
import com.dt8.task_flow.service.UserService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private ProjectService projectService;
    private ProjectMapper projectMapper;
    private UserService userService;

    @Autowired
    public ProjectController(ProjectService projectService, ProjectMapper projectMapper, UserService userService) {
        this.projectService = projectService;
        this.projectMapper = projectMapper;
        this.userService = userService;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable long id, @AuthenticationPrincipal CustomUserDetails currentUser) {
        Optional<User> userOptional = userService.getUserById(currentUser.getId());
        Optional<Project> projectOptional = projectService.getProjectById(id);

        if (projectOptional.isPresent() && userOptional.isPresent())  {
            User user = userOptional.get();
            Project project = projectOptional.get();

            // check if the user owns the project
            if (project.getOwner().getId() == user.getId()) {
                return ResponseEntity.ok(projectMapper.toProjectDto(project));
            }

            // check if the user is part of the project
            if (project.getUsers().contains(user)) {
                return ResponseEntity.ok(projectMapper.toProjectDto(project));
            }

            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<ProjectDto>> getProjectsByUserId(@PathVariable long id) {
        List<Project> usersProjects = userService.getProjectsByUserId(id);
        usersProjects.addAll(projectService.getProjectsByOwnerId(id));
        return ResponseEntity.ok(
                usersProjects.stream()
                .map(projectMapper::toProjectDto)
                .collect(Collectors.toList())
        );
    }

    @PostMapping("/")
    public ResponseEntity<ProjectDto> createProject(@Valid @RequestBody CreateProjectRequest createProjectRequest, @AuthenticationPrincipal CustomUserDetails currentUser, UserService userService) {
        Project projectToCreate = projectMapper.toProject(createProjectRequest, currentUser, userService);
        Project project = projectService.createProject(projectToCreate);
        return ResponseEntity.ok(projectMapper.toProjectDto(project));
    }

    @PutMapping("/id/{id}/add/{userId}")
    public void addUserToProjectById(@PathVariable long id, @PathVariable long userId) {
        projectService.addUserToProjectById(id, userId);
    }

    @PutMapping("/id/{id}/remove/{userId}")
    public void removeUserFromProjectById(@PathVariable long id, @PathVariable long userId) {
        projectService.removeUserFromProjectById(id, userId);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<ProjectDto> updateProjectById(@Valid @RequestBody UpdateProjectRequest updateProjectRequest, @PathVariable long id, UserService userService) {
        Project projectToUpdate = projectMapper.toProject(updateProjectRequest, userService);
        Project updatedProject = projectService.updateProjectById(id, projectToUpdate);
        return ResponseEntity.ok(projectMapper.toProjectDto(updatedProject));
    }

    @DeleteMapping("/id/{id}")
    public void deleteProject(@PathVariable long id) {
        projectService.deleteProjectById(id);
    }
}