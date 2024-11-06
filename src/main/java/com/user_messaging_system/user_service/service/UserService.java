package com.user_messaging_system.user_service.service;

import com.user_messaging_system.user_service.api.input.UserRegisterInput;
import com.user_messaging_system.user_service.api.input.UserUpdateInput;
import com.user_messaging_system.user_service.api.output.UserRegisterOutput;
import com.user_messaging_system.user_service.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO getCurrentUser(String jwtToken);
    UserDTO getUserById(String id);
    List<UserDTO> getSenderAndReceiverByIds(String jwtToken, String senderId, String receiverId);
    UserDTO getUserByEmail(String email);
    UserRegisterOutput createUser(UserRegisterInput userRegisterInput);
    void deleteCurrentUser(String jwtToken);
    UserDTO updateCurrentUser(String jwtToken, UserUpdateInput userUpdateInput);
}