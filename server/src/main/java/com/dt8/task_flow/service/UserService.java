package com.dt8.task_flow.service;

import com.dt8.task_flow.entity.Project;
import com.dt8.task_flow.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getUsers();

    Optional<User> getUserById(long id);

    User getUserByUsername(String username);

    List<Project> getProjectsByUserId(long id);

    User createUser(User user);

    void updateUser(User user);

    void deleteUser(User user);

    boolean hasUserWithUsername(String username);

    boolean hasUserWithEmail(String email);

    User validateAndGetUserByUsername(String username);
}
