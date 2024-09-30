package com.dt8.task_flow.rest;

import com.dt8.task_flow.entity.User;
import com.dt8.task_flow.mapper.UserMapper;
import com.dt8.task_flow.rest.dto.UserDto;
import com.dt8.task_flow.security.CustomUserDetails;
import com.dt8.task_flow.security.WebSecurityConfig;
import com.dt8.task_flow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;
    private UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable long id, @AuthenticationPrincipal CustomUserDetails currentUser) {
        Optional<User> selfUserOptional = userService.getUserById(currentUser.getId());
        Optional<User> userOptional = userService.getUserById(id);

        if (selfUserOptional.isPresent() && userOptional.isPresent()) {
            User selfUser = selfUserOptional.get();
            User user = userOptional.get();

            if (selfUser.getRole().equals(WebSecurityConfig.ADMIN) || selfUser.getId() == id) {
                return ResponseEntity.ok(userMapper.toUserDto(user));
            }
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser(@AuthenticationPrincipal CustomUserDetails currentUser) {
        return ResponseEntity.ok(userMapper.toUserDto(userService.validateAndGetUserByUsername(currentUser.getUsername())));
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok(
                userService
                .getUsers()
                .stream()
                .map(userMapper::toUserDto)
                .collect(Collectors.toList())
        );
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id) {
        Optional<User> userOptional = userService.getUserById(id);
        userOptional.ifPresent(user -> userService.deleteUser(user));

    }
}
