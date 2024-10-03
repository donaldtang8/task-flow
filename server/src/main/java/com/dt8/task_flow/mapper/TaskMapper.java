package com.dt8.task_flow.mapper;

import com.dt8.task_flow.entity.Task;
import com.dt8.task_flow.rest.dto.CreateTaskRequest;
import com.dt8.task_flow.rest.dto.TaskDto;
import com.dt8.task_flow.rest.dto.UpdateTaskRequest;
import com.dt8.task_flow.security.CustomUserDetails;

public interface TaskMapper {
    Task toTask(CreateTaskRequest createTaskRequest, CustomUserDetails customUserDetails);

    Task toTask(UpdateTaskRequest updateTaskRequest);

    TaskDto toTaskDto(Task task);
}
