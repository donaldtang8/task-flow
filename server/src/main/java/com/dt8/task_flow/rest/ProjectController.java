package com.dt8.task_flow.rest;

import com.dt8.task_flow.entity.Project;
import com.dt8.task_flow.rest.dto.CreateProjectRequest;
import com.dt8.task_flow.rest.dto.ProjectDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
//    @PostMapping("/")
//    public ProjectDto createProject(@Valid @RequestBody CreateProjectRequest createProjectRequest) {
//        String title = createProjectRequest.getTitle();
//        String description = createProjectRequest.getDescription();
//        Project project = projectService.saveProject(
//
//        )
//    }
}