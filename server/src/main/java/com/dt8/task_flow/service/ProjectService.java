package com.dt8.task_flow.service;

import com.dt8.task_flow.entity.Project;
import com.dt8.task_flow.entity.Task;
import com.dt8.task_flow.entity.User;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    List<Project> getProjectsByOwnerId(long ownerId);

    Optional<Project> getProjectById(long projectId);

    Project createProject(Project project);

    Project addUserToProjectById(Project project, User user);

    Project removeUserFromProjectById(Project project, User user);

    Project addTaskToProjectById(Project project, Task task);

    Project removeTaskFromProjectById(Project project, Task task);

    Project updateProjectById(long projectId, Project updatedProject);

    void deleteProjectById(long projectId);

    Project validateAndGetProjectById(long projectId);

    boolean userHasProjectPermission(Project project);
}
