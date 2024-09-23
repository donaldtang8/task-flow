package com.dt8.task_flow.rest.dto;

public record UserDto(Long id, String username, String firstName, String lastName, String email, String role) {
}