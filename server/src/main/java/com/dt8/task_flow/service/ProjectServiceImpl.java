package com.dt8.task_flow.service;

import com.dt8.task_flow.entity.Project;
import com.dt8.task_flow.entity.User;
import com.dt8.task_flow.repository.ProjectRepository;
import com.dt8.task_flow.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, UserService userService, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public List<Project> getProjectsByOwnerId(long ownerId) {
        return projectRepository.findByOwnerId(ownerId);
    }

    @Override
    public List<Project> getProjectsByUserId(long userId, boolean includeOwned) {
        List<Project> projectList = new ArrayList<>();
        if (userService.validateUserById(userId)) {
            projectList.addAll(userRepository.findProjectsByUserId(userId));
        }
        if (includeOwned) {
            projectList.addAll(getProjectsByOwnerId(userId));
        }
        return projectList;
    }

    @Override
    public Optional<Project> getProjectById(long projectId) {
        return projectRepository.findById(projectId);
    }

    @Override
    @Transactional
    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    @Transactional
    public Project addUserToProjectById(long projectId, long userId) {
        Project project = getProjectById(projectId).get();
        User user = userService.getUserById(userId).get();
        project.addUser(user);
        projectRepository.save(project);
        user.addProject(project);
        userRepository.save(user);
        return project;
    }

    @Override
    @Transactional
    public Project removeUserFromProjectById(long projectId, long userId) {
        Project project = getProjectById(projectId).get();
        User user = userService.getUserById(userId).get();
        project.removeUser(user);
        projectRepository.save(project);
        user.removeProject(project);
        userRepository.save(user);
        return project;
    }

    @Override
    @Transactional
    public Project updateProjectById(long projectId, Project updatedProject) {
        Project project = projectRepository.findById(projectId).get();
        project.setTitle(updatedProject.getTitle());
        project.setDescription(updatedProject.getDescription());
        project.setStatus(updatedProject.getStatus());
        projectRepository.save(project);
        return project;
    }

    @Override
    @Transactional
    public void deleteProjectById(long projectId) {
        projectRepository.deleteById(projectId);
    }

    @Override
    public boolean validateProjectById(long projectId) {
        Optional<Project> projectOptional = getProjectById(projectId);
        return projectOptional.isPresent();
    }

    @Override
    public boolean validateProjectPermissionById(long projectId) {
        User user = userService.getCurrentUser();
        Optional<Project> projectOptional = getProjectById(projectId);
        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();
            return project.getUsers().contains(user) || project.getOwner().equals(user);
        }
        return false;
    }
}
