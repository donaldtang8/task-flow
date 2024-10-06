package com.dt8.task_flow.rest.dto;

import com.dt8.task_flow.entity.UserRole;

public record UserDto(long id, String username, String firstName, String lastName, String email, UserRole role) {
}