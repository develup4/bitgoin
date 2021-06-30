package com.microservice.userservice.service;

import com.microservice.userservice.dto.UserDto;

public interface UserService {
    UserDto createUser(UserDto userDto);
}
