package com.dt8.task_flow.rest.dto;

public record AuthResponse(UserDto user, String accessToken) {
}
