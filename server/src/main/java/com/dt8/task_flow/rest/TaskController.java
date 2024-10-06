package com.dt8.task_flow.rest;

import com.dt8.task_flow.entity.Task;
import com.dt8.task_flow.mapper.TaskMapper;
import com.dt8.task_flow.rest.dto.CreateTaskRequest;
import com.dt8.task_flow.rest.dto.TaskDto;
import com.dt8.task_flow.rest.dto.UpdateTaskRequest;
import com.dt8.task_flow.security.CustomUserDetails;
import com.dt8.task_flow.service.ProjectService;
import com.dt8.task_flow.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskMapper taskMapper;
    private final TaskService taskService;
    private final ProjectService projectService;

    @Autowired
    public TaskController(TaskMapper taskMapper, TaskService taskService, ProjectService projectService) {
        this.taskMapper = taskMapper;
        this.taskService = taskService;
        this.projectService = projectService;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable long id) {
        if (taskService.validateTaskById(id) && taskService.validateTaskPermissionById(id)) {
            Task task = taskService.getTaskById(id).get();
            return ResponseEntity.ok(taskMapper.toTaskDto(task));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<TaskDto>> getTasksByProjectId(@PathVariable long projectId) {
        if (projectService.validateProjectById(projectId) && projectService.validateProjectPermissionById(projectId)) {
            List<Task> projectTasks = taskService.getTasksByProjectId(projectId);
            return ResponseEntity.ok(
                    projectTasks.stream()
                            .map(taskMapper::toTaskDto)
                            .collect(Collectors.toList())
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping()
    public ResponseEntity<TaskDto> createTask(@Valid @RequestBody CreateTaskRequest createTaskRequest, @AuthenticationPrincipal CustomUserDetails currentUser) {
        Task taskToCreate = taskMapper.toTask(createTaskRequest, currentUser);
        Task task = taskService.createTask(taskToCreate);
        return ResponseEntity.ok(taskMapper.toTaskDto(task));
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<TaskDto> updateTask(@Valid @RequestBody UpdateTaskRequest updateTaskRequest, @PathVariable long id) {
        if (taskService.validateTaskById(id) && taskService.validateTaskPermissionById(id)) {
            Task taskToUpdate = taskMapper.toTask(updateTaskRequest);
            Task updatedTask = taskService.updateTaskById(id, taskToUpdate);
            return ResponseEntity.ok(taskMapper.toTaskDto(updatedTask));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<HttpStatus> deleteTask(@PathVariable long id) {
        if (taskService.validateTaskById(id) && taskService.validateTaskPermissionById(id)) {
            Task task = taskService.getTaskById(id).get();
            taskService.deleteTaskById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
