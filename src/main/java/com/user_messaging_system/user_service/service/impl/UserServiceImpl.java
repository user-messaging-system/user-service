package com.user_messaging_system.user_service.service.impl;

import com.user_messaging_system.core_library.exception.UnauthorizedException;
import com.user_messaging_system.core_library.exception.UserNotFoundException;
import com.user_messaging_system.core_library.service.JWTService;
import com.user_messaging_system.user_service.api.input.UserRegisterInput;
import com.user_messaging_system.user_service.api.input.UserUpdateInput;
import com.user_messaging_system.user_service.api.output.UserRegisterOutput;
import com.user_messaging_system.user_service.common.enumerator.Role;
import com.user_messaging_system.user_service.dto.UserDTO;
import com.user_messaging_system.user_service.mapper.UserMapper;
import com.user_messaging_system.user_service.model.User;
import com.user_messaging_system.user_service.repository.RoleRepository;
import com.user_messaging_system.user_service.repository.UserRepository;
import com.user_messaging_system.user_service.service.UserService;
import jakarta.persistence.EntityExistsException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, JWTService jwtService, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDTO getCurrentUser(String token){
        token = jwtService.extractToken(token);
        jwtService.validateToken(token);
        String email = jwtService.extractEmail(token);
        return getUserByEmail(email);
    }

    @Override
    public List<UserDTO> getSenderAndReceiverByIds(String jwtToken, String senderId, String receiverId){
        String email = jwtService.extractEmail(jwtToken);
        User user = findUserByEmail(email);
        validateUserIsAuthorizedForConversation(user.getId(), senderId, receiverId);
        List<User> senderAndReceiverUsers = userRepository.findAllById(List.of(senderId, receiverId));
        return UserMapper.INSTANCE.userListToUserDTOList(senderAndReceiverUsers);
    }

    @Override
    public UserDTO getUserById(String id){
        User user = findUserById(id);
        return UserMapper.INSTANCE.userToUserDTO(user);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User user = findUserByEmail(email);
        return UserMapper.INSTANCE.userToUserDTO(user);
    }

    @Override
    public UserRegisterOutput createUser(UserRegisterInput userRegisterInput){
        validateUserIsNotExistByEmail(userRegisterInput.email());
        User user = prepareUserForCreate(userRegisterInput);
        user = userRepository.save(user);
        String accessToken = generateTokenForNewUser(user);
        return UserMapper.INSTANCE.userToUserOutputWithAccessToken(user, accessToken);
    }

    @Override
    public UserDTO updateCurrentUser(String token, UserUpdateInput userUpdateInput){
        jwtService.validateToken(token);
        String currentUserEmail = jwtService.extractEmail(token);
        User existingUser = findUserByEmail(currentUserEmail);
        validateUserIsNotExistByEmailAndId(userUpdateInput.email(), existingUser.getId());
        existingUser = UserMapper.INSTANCE.updateAndReturnUser(userUpdateInput, existingUser);
        return UserMapper.INSTANCE.userToUserDTO(existingUser);
    }

    @Override
    public void deleteCurrentUser(String jwtToken){
        jwtService.validateToken(jwtToken);
        String email = jwtService.extractEmail(jwtToken);
        User user = findUserByEmail(email);
        userRepository.delete(user);
    }

    private User findUserById(String id){
        return userRepository.findById(id).
                orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    private User findUserByEmail(String email){
        return userRepository.findByEmail(email).
                orElseThrow(() -> new com.user_messaging_system.core_library.exception.UserNotFoundException("User not found"));
    }

    private void validateUserIsNotExistByEmail(String email){
        if(userRepository.existsByEmail(email)){
            throw new EntityExistsException("This email address (" + email + ") is already in use.");
        }
    }

    private void validateUserIsNotExistByEmailAndId(String email, String id){
        if(userRepository.existsByEmailAndIdNot(email, id)){
            throw new EntityExistsException("This email address (" + email + ") is already in use.");
        }
    }

    private String generateTokenForNewUser(User user){
        return jwtService.generateJwtToken(user.getEmail(), user.getId(), List.of(Role.USER.getValue()));
    }

    private com.user_messaging_system.user_service.model.Role getRoleForUser(){
        return roleRepository.findByName(Role.USER.getValue()).
                orElseThrow(() -> new UserNotFoundException("Role not found"));
    }

    private User prepareUserForCreate(UserRegisterInput userRegisterInput){
        User user = UserMapper.INSTANCE.userRegisterInputToUser(userRegisterInput);
        user.getRoles().add(getRoleForUser());
        return user;
    }

    //TODO: throw new ile firlatilan hatayi duzelt simdilik UserNotFoundException olarak biraktim.
    private void validateUserIsAuthorizedForConversation(String currentUserId, String senderId, String receiverId){
        if(currentUserId.equals(senderId) || currentUserId.equals(receiverId)){
            return;
        }else{
            throw new UnauthorizedException("Current user is not authorized to access this conversation.");
        }
    }
}
