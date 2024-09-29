package com.dt8.task_flow.mapper;

import com.dt8.task_flow.entity.User;
import com.dt8.task_flow.rest.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMapper {
    public UserDto toUserDto(User user) {
        if (user == null) {
            return null;
        }
        return new UserDto(user.getId(), user.getUsername(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getRole());
    }

    public List<UserDto> toUserDto(List<User> users) {
        return users
                .stream()
                .map(this::toUserDto)
                .collect(Collectors.toList());
    }
}
