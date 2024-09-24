package com.dt8.task_flow.rest;

import com.dt8.task_flow.entity.User;
import com.dt8.task_flow.mapper.UserMapper;
import com.dt8.task_flow.rest.dto.UserDto;
import com.dt8.task_flow.security.CustomUserDetails;
import com.dt8.task_flow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;
    private UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        userService = userService;
        userMapper = userMapper;
    }

    @GetMapping("/me")
    public UserDto getCurrentUser(@AuthenticationPrincipal CustomUserDetails currentUser) {
        return userMapper.toUserDto(userService.validateAndGetUserByUsername(currentUser.getUsername()));
    }

    @GetMapping("/")
    public List<UserDto> getUsers() {
        System.out.println("Getting users");
        return userService
                .getUsers()
                .stream()
                .map(userMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{username}")
    public UserDto getUser(@PathVariable String username) {
        return userMapper.toUserDto(userService.validateAndGetUserByUsername(username));
    }
}
