package com.dt8.task_flow.service;

import com.dt8.task_flow.entity.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    List<Project> getProjectsByOwnerId(long ownerId);

    Optional<Project> getProjectById(long projectId);

    Project createProject(Project project);

    void addUserToProjectById(long projectId, long userId);

    void removeUserFromProjectById(long projectId, long userId);

    Project updateProjectById(long projectId, Project udpatedProject);

    void deleteProjectById(long projectId);
}
