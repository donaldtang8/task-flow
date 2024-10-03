package com.dt8.task_flow.mapper;

import com.dt8.task_flow.entity.Project;
import com.dt8.task_flow.entity.Task;
import com.dt8.task_flow.rest.dto.CreateTaskRequest;
import com.dt8.task_flow.rest.dto.TaskDto;
import com.dt8.task_flow.rest.dto.TaskProjectDto;
import com.dt8.task_flow.rest.dto.UpdateTaskRequest;
import com.dt8.task_flow.security.CustomUserDetails;

import java.util.List;

public interface TaskMapper {
    Task toTask(CreateTaskRequest createTaskRequest, CustomUserDetails customUserDetails);

    Task toTask(UpdateTaskRequest updateTaskRequest);

    TaskDto toTaskDto(Task task);

    List<TaskDto> toTaskDto(List<Task> tasks);

    TaskProjectDto toTaskProjectDto(Project project);
}
