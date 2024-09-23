package com.dt8.task_flow.service;

import com.dt8.task_flow.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getUsers();

    Optional<User> getUserByUsername(String username);

    boolean hasUserWithUsername(String username);

    boolean hasUserWithEmail(String email);

    User validateAndGetUserByUsername(String username);

    Optional<User> validUsernameAndPassword(String username, String password);

    User saveUser(User user);

    void deleteUser(User user);
}
