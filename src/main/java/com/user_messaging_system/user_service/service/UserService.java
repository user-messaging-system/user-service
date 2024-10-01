package com.user_messaging_system.user_service.service;

import com.user_messaging_system.user_service.api.input.UserInput;
import com.user_messaging_system.user_service.api.output.UserCreateOutput;
import com.user_messaging_system.user_service.api.output.UserOutput;

public interface UserService {
    UserOutput getUserById(String id);
    UserOutput getUserByEmail(String email);
    UserCreateOutput createUser(UserInput userInput);
    UserOutput updateUserById(String id, UserInput userInput);
    void deleteUserById(String id);
}