package com.dt8.task_flow.service;

import com.dt8.task_flow.entity.Project;
import com.dt8.task_flow.entity.Task;
import com.dt8.task_flow.entity.User;
import com.dt8.task_flow.repository.ProjectRepository;
import com.dt8.task_flow.repository.TaskRepository;
import com.dt8.task_flow.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    private final UserService userService;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, TaskRepository taskRepository, UserRepository userRepository, UserService userService) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    @Override
    public List<Project> getProjectsByOwnerId(long ownerId) {
        return projectRepository.findByOwnerId(ownerId);
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
    public Project addUserToProjectById(Project project, User user) {
        project.addUser(user);
        projectRepository.save(project);
        return project;
    }

    @Override
    @Transactional
    public Project removeUserFromProjectById(Project project, User user) {
        project.removeUser(user);
        projectRepository.save(project);
        return project;
    }

    @Override
    @Transactional
    public Project addTaskToProjectById(Project project, Task task) {
        project.addTask(task);
        projectRepository.save(project);
        return project;
    }

    @Override
    @Transactional
    public Project removeTaskFromProjectById(Project project, Task task) {
        project.removeTask(task);
        projectRepository.save(project);
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
    public Project validateAndGetProjectById(long projectId) {
        Optional<Project> projectOptional = getProjectById(projectId);
        return projectOptional.orElse(null);
    }

    @Override
    public boolean userHasProjectPermission(Project project) {
        User user = userService.getCurrentUser();
        if (project != null) {
            return project.getUsers().contains(user) || project.getOwner().equals(user);
        }
        return false;
    }
}
