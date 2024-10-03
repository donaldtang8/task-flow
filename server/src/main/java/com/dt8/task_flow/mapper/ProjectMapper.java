package com.dt8.task_flow.mapper;

import com.dt8.task_flow.entity.Project;
import com.dt8.task_flow.rest.dto.CreateProjectRequest;
import com.dt8.task_flow.rest.dto.ProjectDto;
import com.dt8.task_flow.rest.dto.UpdateProjectRequest;
import com.dt8.task_flow.security.CustomUserDetails;
import com.dt8.task_flow.service.UserService;

public interface ProjectMapper {
    Project toProject(CreateProjectRequest createProjectRequest);

    Project toProject(UpdateProjectRequest updateProjectRequest);

    ProjectDto toProjectDto(Project project);
}
