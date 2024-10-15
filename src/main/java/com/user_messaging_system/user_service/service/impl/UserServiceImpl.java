package com.user_messaging_system.user_service.service.impl;

import com.user_messaging_system.core_library.exception.UserNotFoundException;
import com.user_messaging_system.user_service.api.input.UserInput;
import com.user_messaging_system.user_service.api.output.UserCreateOutput;
import com.user_messaging_system.user_service.api.output.UserOutput;
import com.user_messaging_system.user_service.common.enumerator.Role;
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
    private final JWTServiceImpl jwtService;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, JWTServiceImpl jwtService, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.roleRepository = roleRepository;
    }

    public UserOutput getUserById(String id){
        User user = findUserById(id);
        return UserMapper.INSTANCE.userToUserOutput(user);
    }

    @Override
    public UserOutput getUserByEmail(String email) {
        User user = findUserByEmail(email);
        return UserMapper.INSTANCE.userToUserOutput(user);
    }

    public UserCreateOutput createUser(UserInput userInput){
        checkUserIsExistByEmail(userInput.email());
        User user = prepareUserForCreate(userInput);
        String accessToken = generateTokenForNewUser(user);
        userRepository.save(user);
        return UserMapper.INSTANCE.userToUserOutputWithAccessToken(user, accessToken);
    }


    @Override
    public UserOutput updateUserById(String id, UserInput userInput) {
        checkUserIsExistByIdForUpdate(userInput.email(), id);
        User user = UserMapper.INSTANCE.userInputToUserForUpdate(userInput);
        userRepository.save(user);
        return UserMapper.INSTANCE.userToUserOutput(user);
    }

    //:TODO: Kullanicinin tum verilerini silebilecek durumu ele aldiktan sonra silme islemini gerceklestir.
    //:TODO: Silme islemi parola ile gerceklessin.
    public void deleteUserById(String id){
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

    private void checkUserIsExistByEmail(String email){
        if(userRepository.existsByEmail(email)){
            throw new EntityExistsException("This email address (" + email + ") is already in use.");
        }
    }

    private void checkUserIsExistByIdForUpdate(String email, String id){
        if(!userRepository.existsByEmailAndIdNot(email, id)){
            throw new EntityExistsException("This email address (" + email + ") is already in use.");
        }
    }

    private String generateTokenForNewUser(User user){
        return jwtService.generateJwtToken(user.getEmail(), List.of(Role.USER.getValue()));
    }

    private com.user_messaging_system.user_service.model.Role getRoleForUser(){
        return roleRepository.findByName(Role.USER.getValue()).
                orElseThrow(() -> new UserNotFoundException("Role not found"));
    }

    private User prepareUserForCreate(UserInput userInput){
        User user = UserMapper.INSTANCE.userInputToUser(userInput);

        user.getRoles().add(getRoleForUser());
        return user;
    }
}