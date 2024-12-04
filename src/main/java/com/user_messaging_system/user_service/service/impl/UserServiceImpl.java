package com.user_messaging_system.user_service.service.impl;

import com.user_messaging_system.core_library.exception.UnauthorizedException;
import com.user_messaging_system.core_library.exception.UserNotFoundException;
import com.user_messaging_system.core_library.service.JWTService;
import com.user_messaging_system.user_service.api.input.UserRegisterInput;
import com.user_messaging_system.user_service.api.input.UserUpdateInput;
import com.user_messaging_system.user_service.common.enumerator.Role;
import com.user_messaging_system.user_service.dto.UserDTO;
import com.user_messaging_system.user_service.dto.UserRegisterDTO;
import com.user_messaging_system.user_service.exception.EmailAlreadyExistException;
import com.user_messaging_system.user_service.mapper.UserMapper;
import com.user_messaging_system.user_service.model.User;
import com.user_messaging_system.user_service.repository.RoleRepository;
import com.user_messaging_system.user_service.repository.UserRepository;
import com.user_messaging_system.user_service.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserServiceImpl(
            UserRepository userRepository,
            JWTService jwtService,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            UserMapper userMapper
    ) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO getCurrentUser(String token){
        jwtService.validateToken(token);
        String email = jwtService.extractEmail(token);
        return userMapper.userToUserDTO(findUserByEmail(email));
    }

    @Override
    public List<UserDTO> getSenderAndReceiverByIds(String jwtToken, String senderId, String receiverId){
        String token = jwtService.extractToken(jwtToken);
        User user = findUserById(token);

        validateUserIsAuthorizedForConversation(user.getId(), senderId, receiverId);
        List<User> senderAndReceiverUsers = userRepository.findAllById(List.of(senderId, receiverId));
        return userMapper.userListToUserDTOList(senderAndReceiverUsers);
    }

    @Override
    public UserDTO getUserById(String id){
        validateUUIDFormatAndNotBlank(id);
        User user = findUserById(id);
        return userMapper.userToUserDTO(user);
    }

    @Override
    public UserDTO getUserByEmail(String email){
        validateEmailFormatAndNotBlank(email);
        User user = findUserByEmail(email);
        return userMapper.userToUserDTO(user);
    }

    @Override
    public UserRegisterDTO createUser(UserRegisterInput userRegisterInput){
        validateUserIsNotExistByEmail(userRegisterInput.email());
        User user = prepareUserForCreate(userRegisterInput);
        user = userRepository.save(user);
        String accessToken = generateTokenForNewUser(user);
        return userMapper.userToUserRegisterDTO(user, accessToken);
    }

    @Override
    public UserDTO updateUserById(String id, String token, UserUpdateInput userUpdateInput){
        jwtService.validateToken(token);
        String currentUserId = jwtService.extractUserId(token);
        validateUserAuthorization(id, currentUserId);
        User currentUser = findUserById(currentUserId);
        validateUserIsNotExistByEmailAndId(userUpdateInput.email(), currentUser.getId());
        currentUser = userMapper.updateAndReturnUser(userUpdateInput, currentUser);
        return userMapper.userToUserDTO(currentUser);
    }

    @Override
    public void deleteUser(String id, String token){
        jwtService.validateToken(token);
        String currentUserId = jwtService.extractUserId(token);
        validateUserAuthorization(id, currentUserId);
        User user = findUserById(id);
        userRepository.delete(user);
    }

    private User findUserById(String id){
        return userRepository.findById(id).
                orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    private User findUserByEmail(String email){
        return userRepository.findByEmail(email).
                orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    private void validateUserIsNotExistByEmail(String email){
        if(userRepository.existsByEmail(email)){
            throw new EmailAlreadyExistException("This email address (" + email + ") is already in use.");
        }
    }

    private void validateUserIsNotExistByEmailAndId(String email, String id){
        if(userRepository.existsByEmailAndIdNot(email, id)){
            throw new EmailAlreadyExistException("This email address (" + email + ") is already in use.");
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
        User user = new User();
        user.setEmail(userRegisterInput.email());
        user.setName(userRegisterInput.name());
        user.setLastName(userRegisterInput.lastName());
        user.setPassword(passwordEncoder.encode(userRegisterInput.password()));
        user.getRoles().add(getRoleForUser());
        return user;
    }

    private void validateUserIsAuthorizedForConversation(String currentUserId, String senderId, String receiverId){
        if(!(currentUserId.equals(senderId) || currentUserId.equals(receiverId))){
            throw new UnauthorizedException(
                "Current user (ID: " + currentUserId + ") is not authorized to access the conversation:\n" +
                    "- Sender (ID: " + senderId + ")\n" +
                    "- Receiver (ID: " + receiverId + ")"
            );
        }
    }

    private void validateUserAuthorization(String id, String currentUserId) {
        if (!id.equals(currentUserId)) {
            throw new UnauthorizedException("User is not authorized for this operation.");
        }
    }

    private void validateUUIDFormatAndNotBlank(String id){
        if(Objects.isNull(id) || id.isBlank()){
            throw new IllegalArgumentException("Id is not a valid UUID.");
        }

        try{
            UUID.fromString(id);
        }catch (IllegalArgumentException ex){
            throw new IllegalArgumentException("Invalid UUID");
        }
    }

    private void validateEmailFormatAndNotBlank(String email){
        String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        if(Objects.isNull(email) || email.isBlank()){
            throw new IllegalArgumentException("Email address is blank.");
        }

        if (!Pattern.matches(EMAIL_REGEX, email)) {
            throw new IllegalArgumentException("Invalid email address.");
        }
    }
}
