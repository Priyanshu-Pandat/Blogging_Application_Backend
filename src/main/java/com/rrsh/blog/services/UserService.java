package com.rrsh.blog.services;

import com.rrsh.blog.entities.User;
import com.rrsh.blog.model.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto user);
    UserDto updateUser(UserDto user, Integer userId);
    UserDto getUserById(Integer id);
    List<UserDto> getAllUsers();
    void deleteUser(Integer userId);
}
