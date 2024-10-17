package com.user_messaging_system.user_service.mapper;

import com.user_messaging_system.user_service.api.input.UserInput;
import com.user_messaging_system.user_service.api.output.UserCreateOutput;
import com.user_messaging_system.user_service.api.output.UserOutput;
import com.user_messaging_system.user_service.dto.UserDTO;
import com.user_messaging_system.user_service.model.Role;
import com.user_messaging_system.user_service.model.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "roles", source = "roles", qualifiedByName = "mapRoles")
    UserOutput userToUserOutput(User user);

    @Mapping(target = "roles", source = "user.roles", qualifiedByName = "mapRoles")
    @Mapping(target = "accessToken", source = "accessToken")
    UserCreateOutput userToUserOutputWithAccessToken(User user, String accessToken);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "mailVerified", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User userInputToUserForUpdate(UserInput user);

    @Mapping(target = "mailVerified", ignore = true)
    User userInputToUser(UserInput userInput);

    List<UserDTO> userListToUserDTOList(List<User> userList);

    List<UserOutput> userDtoListToUserOutputList(List<UserDTO> userDTOList);

    UserOutput userDtoToUserOutput(UserDTO userDTO);

    @Mapping(target = "roles", source = "roles", qualifiedByName = "mapRoles")
    UserDTO userToUserDTO(User user);

    @Named("mapRoles")
    default List<String> mapRoles(List<Role> roles) {
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }

    default String mapAccessToken(String accessToken) {
        return accessToken;
    }
}
