package com.dt8.task_flow.rest;

import com.dt8.task_flow.entity.User;
import com.dt8.task_flow.entity.UserRole;
import com.dt8.task_flow.exception.BadRequestException;
import com.dt8.task_flow.mapper.UserMapper;
import com.dt8.task_flow.rest.dto.AuthResponse;
import com.dt8.task_flow.rest.dto.LoginRequest;
import com.dt8.task_flow.rest.dto.SignupRequest;
import com.dt8.task_flow.security.TokenProvider;
import com.dt8.task_flow.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private UserService userService;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private TokenProvider tokenProvider;

    @Autowired
    public AuthController(UserService userService, UserMapper userMapper, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TokenProvider tokenProvider) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        String token = authenticateAndGetToken(loginRequest.getUsername(), loginRequest.getPassword());
        User user = userService.getUserByUsername(loginRequest.getUsername());
        if (token == null || user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(new AuthResponse(userMapper.toUserDto(user), token));
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signUp(@Valid @RequestBody SignupRequest signupRequest) {
        String email = signupRequest.getEmail();
        String firstName = signupRequest.getFirstName();
        String lastName = signupRequest.getLastName();
        String username = signupRequest.getUsername();
        String password = signupRequest.getPassword();

        if (userService.hasUserWithUsername(username)) {
            throw new BadRequestException("Username has already been used");
        }
        if (userService.hasUserWithEmail(signupRequest.getEmail())) {
            throw new BadRequestException("Email has already been used");
        }

        User user = userService.createUser(new User(email, firstName, lastName, username, passwordEncoder.encode(password), UserRole.USER));

        String token = authenticateAndGetToken(username, password);
        return ResponseEntity.ok(new AuthResponse(userMapper.toUserDto(user), token));
    }

    private String authenticateAndGetToken(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return tokenProvider.generate(authentication);
    }
}