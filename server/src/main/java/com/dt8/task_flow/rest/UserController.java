package com.dt8.task_flow.rest;

import com.dt8.task_flow.entity.User;
import com.dt8.task_flow.mapper.UserMapper;
import com.dt8.task_flow.rest.dto.UserDto;
import com.dt8.task_flow.security.WebSecurityConfig;
import com.dt8.task_flow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping()
    public ResponseEntity<List<UserDto>> getUsers() {
        User selfUser = userService.getCurrentUser();
        String selfUserRole = selfUser.getRole().toString();

        if (selfUserRole.equals(WebSecurityConfig.ADMIN)) {
            return ResponseEntity.ok(
                    userService
                            .getUsers()
                            .stream()
                            .map(userMapper::toUserDto)
                            .collect(Collectors.toList())
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser() {
        User user = userService.getCurrentUser();
        return ResponseEntity.ok(userMapper.toUserDto(user));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable long id) {
        User selfUser = userService.getCurrentUser();
        String selfUserRole = selfUser.getRole().toString();

        if (userService.validateUserById(id) && (selfUserRole.equals(WebSecurityConfig.ADMIN) || selfUser.getId() == id)) {
            User user = userService.getUserById(id).get();
            return ResponseEntity.ok(userMapper.toUserDto(user));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable long id) {
        User selfUser = userService.getCurrentUser();
        String selfUserRole = selfUser.getRole().toString();

        if (userService.validateUserById(id) && (selfUser.getId() == id || selfUserRole.equals(WebSecurityConfig.ADMIN))) {
            User user = userService.getUserById(id).get();
            userService.deleteUser(user);
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}