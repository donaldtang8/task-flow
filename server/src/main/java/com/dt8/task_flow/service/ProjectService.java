package com.dt8.task_flow.service;

import com.dt8.task_flow.entity.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    List<Project> getProjectsByOwnerId(long ownerId);

    Project getProjectById(long projectId);

    Project createProject(Project project);

    Project updateProjectById(long projectId, Project project);

    void addUserToProjectById(long projectId, long userId);

    void removeUserFromProjectById(long projectId, long userId);

    void deleteProjectById(long projectId);
}
