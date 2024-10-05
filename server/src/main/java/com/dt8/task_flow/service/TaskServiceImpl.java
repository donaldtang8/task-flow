package com.dt8.task_flow.service;

import com.dt8.task_flow.entity.Project;
import com.dt8.task_flow.entity.Task;
import com.dt8.task_flow.repository.ProjectRepository;
import com.dt8.task_flow.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final ProjectService projectService;


    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, ProjectRepository projectRepository, ProjectService projectService) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.projectService = projectService;
    }

    @Override
    public Optional<Task> getTaskById(long taskId) {
        return taskRepository.findById(taskId);
    }

    @Override
    public List<Task> getTasksByProjectId(long projectId) {
        return taskRepository.findByProjectId(projectId);
    }

    @Override
    public List<Task> getTasksByUserId(long userId) { return taskRepository.findByAssigneeId(userId); }

    @Override
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    @Transactional
    public Project addTaskToProjectById(long projectId, long taskId) {
        Project project = projectService.getProjectById(projectId).get();
        Task task = getTaskById(taskId).get();
        project.addTask(task);
        projectRepository.save(project);
        return project;
    }

    @Override
    @Transactional
    public Project removeTaskFromProjectById(long projectId, long taskId) {
        Project project = projectService.getProjectById(projectId).get();
        Task task = getTaskById(taskId).get();
        project.removeTask(task);
        projectRepository.save(project);
        return project;
    }

    @Override
    public Task updateTaskById(long taskId, Task updatedTask) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setTitle(updatedTask.getTitle());
            task.setDescription(updatedTask.getDescription());
            task.setStatus(updatedTask.getStatus());
            task.setAssignee(updatedTask.getAssignee());
            task.setAssigner(updatedTask.getAssigner());
            task.setTargetDate(updatedTask.getTargetDate());
            task.setProject(updatedTask.getProject());
            task.setUpdatedAt(LocalDateTime.now());
            taskRepository.save(task);
            return task;
        }
        return null;
    }

    @Override
    public void deleteTaskById(long taskId) {
        taskRepository.deleteById(taskId);
    }

    @Override
    public boolean validateTaskById(long taskId) {
        Optional<Task> taskOptional = getTaskById(taskId);
        return taskOptional.isPresent();
    }

    // checks if user has permission to make changes to the task
    // returns true if user is part of the same project that task is in
    @Override
    public boolean validateTaskPermissionById(long taskId) {
        Optional<Task> task = getTaskById(taskId);
        if (task.isPresent()) {
            Project project = task.get().getProject();
            return projectService.validateProjectPermissionById(project.getId());
        }
        return false;
    }
}
