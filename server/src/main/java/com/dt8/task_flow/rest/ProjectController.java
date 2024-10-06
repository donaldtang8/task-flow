package com.dt8.task_flow.rest;

import com.dt8.task_flow.entity.Project;
import com.dt8.task_flow.entity.User;
import com.dt8.task_flow.mapper.ProjectMapper;
import com.dt8.task_flow.rest.dto.CreateProjectRequest;
import com.dt8.task_flow.rest.dto.ProjectDto;
import com.dt8.task_flow.rest.dto.UpdateProjectRequest;
import com.dt8.task_flow.security.WebSecurityConfig;
import com.dt8.task_flow.service.ProjectService;
import com.dt8.task_flow.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable long id) {
        if (projectService.validateProjectById(id) && projectService.validateProjectPermissionById(id))  {
            Project project = projectService.getProjectById(id).get();
            return ResponseEntity.ok(projectMapper.toProjectDto(project));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<ProjectDto>> getProjectsByUserId(@PathVariable long id) {
        User user = userService.getCurrentUser();
        if (user.getRole().toString().equals(WebSecurityConfig.ADMIN) || user.getId() != id) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        List<Project> usersProjects = userService.getProjectsByUserId(id);
        usersProjects.addAll(projectService.getProjectsByOwnerId(id));
        return ResponseEntity.ok(
                usersProjects.stream()
                .map(projectMapper::toProjectDto)
                .collect(Collectors.toList())
        );
    }

    @PostMapping()
    public ResponseEntity<ProjectDto> createProject(@Valid @RequestBody CreateProjectRequest createProjectRequest) {
        Project projectToCreate = projectMapper.toProject(createProjectRequest);
        Project project = projectService.createProject(projectToCreate);
        return ResponseEntity.ok(projectMapper.toProjectDto(project));
    }

    @PutMapping("/id/{id}/add-user/{userId}")
    public ResponseEntity<ProjectDto> addUserToProjectById(@PathVariable long id, @PathVariable long userId) {
        if (userService.validateUserById(userId) && projectService.validateProjectById(id) && projectService.validateProjectPermissionById(id)) {
            Project updatedProject = projectService.addUserToProjectById(id, userId);
            return ResponseEntity.ok(projectMapper.toProjectDto(updatedProject));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("/id/{id}/remove-user/{userId}")
    public ResponseEntity<ProjectDto> removeUserFromProjectById(@PathVariable long id, @PathVariable long userId) {
        if (userService.validateUserById(userId) && projectService.validateProjectById(id) && projectService.validateProjectPermissionById(id)) {
            Project updatedProject = projectService.removeUserFromProjectById(id, userId);
            return ResponseEntity.ok(projectMapper.toProjectDto(updatedProject));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

//    @PutMapping("/id/{id}/add-task/{taskId}")
//    public ResponseEntity<ProjectDto> addTaskToProjectById(@PathVariable long id, @PathVariable long taskId) {
//        Project project = projectService.validateAndGetProjectById(id);
//        Task task = taskService.validateAndGetTaskById(taskId);
//        if (project != null && task != null && projectService.userHasProjectPermission(project)) {
//            Project updatedProject = projectService.addTaskToProjectById(project, task);
//            return ResponseEntity.ok(projectMapper.toProjectDto(updatedProject));
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//    }
//
//    @PutMapping("/id/{id}/remove-task/{taskId}")
//    public ResponseEntity<ProjectDto> removeTaskFromProjectById(@PathVariable long id, @PathVariable long taskId) {
//        Project project = projectService.validateAndGetProjectById(id);
//        Task task = taskService.validateAndGetTaskById(taskId);
//        if (project != null && task != null && projectService.userHasProjectPermission(project)) {
//            Project updatedProject = projectService.removeTaskFromProjectById(project, task);
//            return ResponseEntity.ok(projectMapper.toProjectDto(updatedProject));
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//    }

    @PutMapping("/id/{id}")
    public ResponseEntity<ProjectDto> updateProjectById(@Valid @RequestBody UpdateProjectRequest updateProjectRequest, @PathVariable long id) {
        if (projectService.validateProjectById(id) && projectService.validateProjectPermissionById(id)) {
            Project projectToUpdate = projectMapper.toProject(updateProjectRequest);
            Project updatedProject = projectService.updateProjectById(id, projectToUpdate);
            return ResponseEntity.ok(projectMapper.toProjectDto(updatedProject));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<HttpStatus> deleteProject(@PathVariable long id) {
        if (projectService.validateProjectById(id) && projectService.validateProjectPermissionById(id)) {
            projectService.deleteProjectById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}