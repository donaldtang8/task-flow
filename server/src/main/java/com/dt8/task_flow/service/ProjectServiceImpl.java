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
    private final UserRepository userRepository;

    private final TaskRepository taskRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
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
    public void addUserToProjectById(long projectId, long userId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));
        User userToAdd = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        project.addUser(userToAdd);
        projectRepository.save(project);
    }

    @Override
    @Transactional
    public void removeUserFromProjectById(long projectId, long userId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));
        User userToRemove = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        project.removeUser(userToRemove);
        projectRepository.save(project);
    }

    @Override
    @Transactional
    public void addTaskToProjectById(long projectId, long taskId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));
        Task taskToAdd = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        project.addTask(taskToAdd);
        projectRepository.save(project);
    }

    @Override
    @Transactional
    public void removeTaskFromProjectById(long projectId, long taskId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));
        Task taskToRemove = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        project.removeTask(taskToRemove);
        projectRepository.save(project);
    }

    @Override
    @Transactional
    public Project updateProjectById(long projectId, Project updatedProject) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));
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
    public boolean userInProject(User user, Project project) {
        System.out.println("User is:");
        System.out.println(user);
        System.out.println("Project is:");
        System.out.println(project);
        System.out.println(project.getUsers());
        System.out.println(project.getOwner());
        return project.getUsers().contains(user) || project.getOwner().equals(user);
    }
}
