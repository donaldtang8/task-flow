package com.dt8.task_flow.rest;

import com.dt8.task_flow.entity.User;
import com.dt8.task_flow.rest.dto.AuthResponse;
import com.dt8.task_flow.rest.dto.LoginRequest;
import com.dt8.task_flow.rest.dto.SignupRequest;
import com.dt8.task_flow.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.RuntimeErrorException;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        Optional<User> userObj = userService.validUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());
        if (userObj.isPresent()) {
            User user = userObj.get();
            return ResponseEntity.ok(new AuthResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getRole()));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/signup")
    public AuthResponse signUp(@Valid @RequestBody SignupRequest signupRequest) {
        String email = signupRequest.getEmail();
        String firstName = signupRequest.getFirstName();
        String lastName = signupRequest.getLastName();
        String username = signupRequest.getUsername();
        String password = signupRequest.getPassword();
        if (userService.hasUserWithUsername(username)) {
            throw new RuntimeException(String.format("Username %s has already been used", signupRequest.getUsername()));
        }
        if (userService.hasUserWithEmail(email)) {
            throw new RuntimeException(String.format("Email %s has already been used", signupRequest.getEmail()));
        }

        User user = userService.createUser(
                new User(
                        email,
                        firstName,
                        lastName,
                        username,
                        password,
                        "USER"
                )
        );
        return new AuthResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getRole());
    }
}
