package com.user_messaging_system.user_service.mapper;

import com.user_messaging_system.user_service.api.input.UserUpdateInput;
import com.user_messaging_system.user_service.api.output.*;
import com.user_messaging_system.user_service.dto.UserDTO;
import com.user_messaging_system.user_service.dto.UserRegisterDTO;
import com.user_messaging_system.user_service.model.Role;
import com.user_messaging_system.user_service.model.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "roles", source = "roles", qualifiedByName = "mapRoles")
    UserGetCurrentOutput userToUserOutput(User user);

    @Mapping(target = "roles", source = "user.roles", qualifiedByName = "mapRoles")
    @Mapping(target = "accessToken", source = "accessToken")
    UserRegisterDTO userToUserRegisterDTO(User user, String accessToken);

    /*@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "mailVerified", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User userInputToUserForUpdate(UserInput user);*/

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User updateAndReturnUser(UserUpdateInput userUpdateInput, @MappingTarget User existingUser);

    /*@Mapping(target = "mailVerified", ignore = true)
    User userInputToUser(UserInput userInput);*/

    UserRegisterOutput userRegisterDTOToUserRegisterOutput(UserRegisterDTO userRegisterDTO);

    List<UserDTO> userListToUserDTOList(List<User> userList);

    List<UserGetCurrentOutput> userDtoListToUserOutputList(List<UserDTO> userDTOList);

    List<UserGetOutput> userDtoListToUserGetOutputList(List<UserDTO> userDTOList);

    UserGetCurrentOutput userDtoToUserOutput(UserDTO userDTO);

    UserGetOutput userDtoToUserGetOutput(UserDTO userDTO);
    UserAuthenticationOutput userDtoToUserAuthenticationOutput(UserDTO userDTO);

    UserUpdateOutput userDtoToUserUpdateOutput(UserDTO userDTO);

    @Mapping(target = "roles", source = "roles", qualifiedByName = "mapRoles")
    UserDTO userToUserDTO(User user);

    @Named("mapRoles")
    default List<String> mapRoles(List<Role> roles) {
        return roles.stream().map(Role::getName).toList();
    }

    default String mapAccessToken(String accessToken) {
        return accessToken;
    }
}
