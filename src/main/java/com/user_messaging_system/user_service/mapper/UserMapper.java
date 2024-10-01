package com.user_messaging_system.user_service.mapper;

import com.user_messaging_system.user_service.api.input.UserInput;
import com.user_messaging_system.user_service.api.output.UserCreateOutput;
import com.user_messaging_system.user_service.api.output.UserOutput;
import com.user_messaging_system.user_service.model.Role;
import com.user_messaging_system.user_service.model.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "roles", expression = "java(mapRoles(user))")
    UserOutput userToUserOutput(User user);

    @Mapping(target = "roles", expression = "java(mapRoles(user))")
    @Mapping(target = "accessToken", expression = "java(mapAccessToken(accessToken))")
    UserCreateOutput userToUserOutputWithAccessToken(User user, String accessToken);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User userInputToUserForUpdate(UserInput user);

    User userInputToUser(UserInput userInput);

    @Named("mapRoles")
    default List<String> mapRoles(User user) {
        return user.getRoles().stream().map(Role::getName).toList();
    }

    default String mapAccessToken(String accessToken) {
        return accessToken;
    }
}
