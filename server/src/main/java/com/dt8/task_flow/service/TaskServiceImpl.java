package com.dt8.task_flow.service;

import com.dt8.task_flow.entity.Task;
import com.dt8.task_flow.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
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
    public List<Task> getTasksByUserId(long userId) {
       return taskRepository.findByAssigneeId(userId);
    }

    @Override
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task updateTaskById(long taskId, Task updatedTask) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
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

    @Override
    public void deleteTaskById(long taskId) {
        taskRepository.deleteById(taskId);
    }
}
