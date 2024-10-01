package com.dt8.task_flow.mapper;

import com.dt8.task_flow.entity.Task;
import com.dt8.task_flow.rest.dto.CreateTaskRequest;
import com.dt8.task_flow.rest.dto.TaskDto;
import com.dt8.task_flow.rest.dto.UpdateTaskRequest;
import com.dt8.task_flow.security.CustomUserDetails;
import com.dt8.task_flow.service.UserService;

public interface TaskMapper {
    Task toTask(CreateTaskRequest createTaskRequest, CustomUserDetails customUserDetails, UserService userService);

    Task toTask(UpdateTaskRequest updateTaskRequest, UserService userService);

    TaskDto toTaskDto(Task task);
}
