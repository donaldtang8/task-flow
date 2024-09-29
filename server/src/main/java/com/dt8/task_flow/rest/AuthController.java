package com.dt8.task_flow.rest;

import com.dt8.task_flow.entity.User;
import com.dt8.task_flow.rest.dto.AuthResponse;
import com.dt8.task_flow.rest.dto.LoginRequest;
import com.dt8.task_flow.rest.dto.SignupRequest;
import com.dt8.task_flow.security.TokenProvider;
import com.dt8.task_flow.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private TokenProvider tokenProvider;

    @Autowired
    public AuthController(UserService userService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TokenProvider tokenProvider) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/authenticate")
    public AuthResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        String token = authenticateAndGetToken(loginRequest.getUsername(), loginRequest.getPassword());
        return new AuthResponse(token);
    }

    @PostMapping("/signup")
    public AuthResponse signUp(@Valid @RequestBody SignupRequest signupRequest) {
        String email = signupRequest.getEmail();
        String firstName = signupRequest.getFirstName();
        String lastName = signupRequest.getLastName();
        String username = signupRequest.getUsername();
        String password = signupRequest.getPassword();

        if (userService.hasUserWithUsername(username)) {
            throw new RuntimeException(String.format("Username %s already been used", username));
        }
        if (userService.hasUserWithEmail(signupRequest.getEmail())) {
            throw new RuntimeException(String.format("Email %s already been used", email));
        }

        userService.createUser(new User(email, firstName, lastName, username, passwordEncoder.encode(password), "USER"));

        String token = authenticateAndGetToken(username, password);
        return new AuthResponse(token);
    }

    private String authenticateAndGetToken(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return tokenProvider.generate(authentication);
    }
}