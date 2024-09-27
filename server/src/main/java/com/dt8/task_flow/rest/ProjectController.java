package com.dt8.task_flow.rest;

import com.dt8.task_flow.entity.Project;
import com.dt8.task_flow.mapper.ProjectMapper;
import com.dt8.task_flow.rest.dto.CreateProjectRequest;
import com.dt8.task_flow.rest.dto.ProjectDto;
import com.dt8.task_flow.rest.dto.UpdateProjectRequest;
import com.dt8.task_flow.security.CustomUserDetails;
import com.dt8.task_flow.service.ProjectService;
import com.dt8.task_flow.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ProjectDto getProjectById(@PathVariable long id) {
        Project project = projectService.getProjectById(id);
        return projectMapper.toProjectDto(project);
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
    public ProjectDto updateProjectById(@Valid @RequestBody UpdateProjectRequest updateProjectRequest, @PathVariable long id, UserService userService) {
        Project projectToUpdate = projectMapper.toProject(updateProjectRequest, userService);
        Project updatedProject = projectService.updateProjectById(id, projectToUpdate);
        return projectMapper.toProjectDto(updatedProject);
    }

    @DeleteMapping("/id/{id}")
    public void deleteProject(@PathVariable long id) {
        projectService.deleteProjectById(id);
    }

    @GetMapping("/user/{id}")
    public List<ProjectDto> getProjectsByUserId(@PathVariable long id) {
        List<Project> usersProjects = userService.getProjectsByUserId(id);
        usersProjects.addAll(projectService.getProjectsByOwnerId(id));
        return usersProjects.stream()
                .map(projectMapper::toProjectDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/")
    public ProjectDto createProject(@Valid @RequestBody CreateProjectRequest createProjectRequest, @AuthenticationPrincipal CustomUserDetails currentUser, UserService userService) {
        Project projectToCreate = projectMapper.toProject(createProjectRequest, currentUser, userService);
        Project project = projectService.createProject(projectToCreate);
        return projectMapper.toProjectDto(project);
    }
}