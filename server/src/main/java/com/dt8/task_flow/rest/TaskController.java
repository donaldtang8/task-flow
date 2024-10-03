package com.dt8.task_flow.rest;

import com.dt8.task_flow.entity.Project;
import com.dt8.task_flow.entity.Task;
import com.dt8.task_flow.entity.User;
import com.dt8.task_flow.mapper.TaskMapper;
import com.dt8.task_flow.rest.dto.CreateTaskRequest;
import com.dt8.task_flow.rest.dto.TaskDto;
import com.dt8.task_flow.rest.dto.UpdateTaskRequest;
import com.dt8.task_flow.security.CustomUserDetails;
import com.dt8.task_flow.service.ProjectService;
import com.dt8.task_flow.service.TaskService;
import com.dt8.task_flow.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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
    public ResponseEntity<TaskDto> getTaskById(@PathVariable long id, @AuthenticationPrincipal CustomUserDetails currentUser) {
        Task task = taskService.validateAndGetTaskById(id);
        if (task != null && taskService.userHasTaskPermission(task)) {
            return ResponseEntity.ok(taskMapper.toTaskDto(task));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/project/{id}")
    public ResponseEntity<List<TaskDto>> getTasksByProjectId(@PathVariable long id, @AuthenticationPrincipal CustomUserDetails currentUser) {
        Project project = projectService.validateAndGetProjectById(id);
        if (project != null && projectService.userHasProjectPermission(project)) {
            List<Task> projectTasks = taskService.getTasksByProjectId(id);
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
        Task task = taskService.validateAndGetTaskById(id);
        if (task != null && taskService.userHasTaskPermission(task)) {
            Task taskToUpdate = taskMapper.toTask(updateTaskRequest);
            Task updatedTask = taskService.updateTaskById(id, taskToUpdate);
            return ResponseEntity.ok(taskMapper.toTaskDto(updatedTask));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<HttpStatus> deleteTask(@PathVariable long id) {
        Task task = taskService.validateAndGetTaskById(id);
        if (task != null && taskService.userHasTaskPermission(task)) {
            taskService.deleteTaskById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
