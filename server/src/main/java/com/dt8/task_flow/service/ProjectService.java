package com.dt8.task_flow.service;

import com.dt8.task_flow.entity.Project;
import com.dt8.task_flow.entity.Task;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    Optional<Project> getProjectById(long projectId);

    List<Project> getProjectsByOwnerId(long ownerId);

    List<Project> getProjectsByUserId(long userId, boolean includeOwned);

    Project createProject(Project project);

    Project addUserToProjectById(long projectId, long userId);

    Project removeUserFromProjectById(long projectId, long userId);

    Project updateProjectById(long projectId, Project updatedProject);

    void deleteProjectById(long projectId);

    boolean validateProjectById(long projectId);

    boolean validateProjectPermissionById(long projectId);
}
