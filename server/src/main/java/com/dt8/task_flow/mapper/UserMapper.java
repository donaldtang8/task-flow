package com.dt8.task_flow.mapper;

import com.dt8.task_flow.entity.User;
import com.dt8.task_flow.rest.dto.UserDto;

import java.util.List;

public interface UserMapper {
    UserDto toUserDto(User user);

    List<UserDto> toUserDto(List<User> users);
}
