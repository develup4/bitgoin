package com.microservice.userservice.service;

import com.microservice.userservice.dto.UserDto;
import com.microservice.userservice.jpa.UserEntity;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto getUserByUserId(String userId);
    Iterable<UserEntity> getUserByAll();
}
