package com.dt8.task_flow.service;

import com.dt8.task_flow.entity.Project;
import com.dt8.task_flow.entity.User;
import com.dt8.task_flow.security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getUsers();

    Optional<User> getUserById(long userId);

    User getUserByUsername(String username);

    List<Project> getProjectsByUserId(long userId);

    User createUser(User user);

    void updateUser(User user);

    void deleteUser(User user);

    boolean hasUserWithUsername(String username);

    boolean hasUserWithEmail(String email);

    User validateAndGetUserByUsername(String username);

    User validateAndGetUserById(long userId);

    User getCurrentUser();
}
