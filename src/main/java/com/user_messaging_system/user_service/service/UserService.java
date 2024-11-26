package com.user_messaging_system.user_service.service;

import com.user_messaging_system.user_service.api.input.UserRegisterInput;
import com.user_messaging_system.user_service.api.input.UserUpdateInput;
import com.user_messaging_system.user_service.dto.UserDTO;
import com.user_messaging_system.user_service.dto.UserRegisterDTO;

import java.util.List;

public interface UserService {
    UserDTO getCurrentUser(String jwtToken);
    UserDTO getUserById(String id);
    UserDTO getUserByEmail(String email);
    List<UserDTO> getSenderAndReceiverByIds(String jwtToken, String senderId, String receiverId);
    UserRegisterDTO createUser(UserRegisterInput userRegisterInput);
    void deleteUser(String id, String jwtToken);
    UserDTO updateUserById(String id, String jwtToken, UserUpdateInput userUpdateInput);
}