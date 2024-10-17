package com.user_messaging_system.user_service.service;

import com.user_messaging_system.user_service.api.input.UserInput;
import com.user_messaging_system.user_service.api.output.UserCreateOutput;
import com.user_messaging_system.user_service.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO getUserById(String id);
    List<UserDTO> getSenderAndReceiverByIds(String senderId, String receiverId);
    UserDTO getUserByEmail(String email);
    UserCreateOutput createUser(UserInput userInput);
    UserDTO updateUserById(String id, UserInput userInput);
    void deleteUserById(String id);
}