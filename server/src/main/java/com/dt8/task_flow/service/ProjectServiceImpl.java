package com.dt8.task_flow.service;

import com.dt8.task_flow.entity.Project;
import com.dt8.task_flow.entity.User;
import com.dt8.task_flow.repository.ProjectRepository;
import com.dt8.task_flow.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Project> getProjectsByOwnerId(long ownerId) {
        return projectRepository.findByOwnerId(ownerId);
    }

    @Override
    public Project getProjectById(long projectId) {
        return null;
    }

    @Override
    @Transactional
    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Project updateProjectById(long projectId, Project project) {
        return null;
    }

    @Override
    @Transactional
    public void addUserToProjectById(long projectId, long userId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));
        User userToAdd = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        project.addUser(userToAdd);
        projectRepository.save(project);
    }

    @Override
    public void removeUserFromProjectById(long projectId, long userId) {

    }

    @Override
    public void deleteProjectById(long projectId) {

    }
}
