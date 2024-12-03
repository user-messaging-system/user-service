package com.user_messaging_system.user_service.util;

import com.user_messaging_system.user_service.api.input.UserRegisterInput;
import com.user_messaging_system.user_service.constant.RoleTestContant;
import com.user_messaging_system.user_service.constant.UserTestConstant;
import com.user_messaging_system.user_service.dto.UserDTO;
import com.user_messaging_system.user_service.model.BaseModel;
import com.user_messaging_system.user_service.model.Role;
import com.user_messaging_system.user_service.model.User;

import java.lang.reflect.Field;
import java.util.List;

import static com.user_messaging_system.user_service.constant.RoleTestContant.*;
import static com.user_messaging_system.user_service.constant.UserTestConstant.*;

public final class UserServiceUtil {
    private UserServiceUtil() {
        throw new AssertionError("Cannot instantiate utility class");
    }

    public static User createUser() throws NoSuchFieldException, IllegalAccessException {
        User user = new User();
        user.setName(NAME);
        user.setLastName(LASTNAME);
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);
        user.setMailVerified(MAIL_VERIFIED);
        user.setRoles(List.of(createRole()));

        Field idField = BaseModel.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(user, UserTestConstant.ID);
        return user;
    }

    public static UserDTO createUserDTO() {
        return new UserDTO(
            UserTestConstant.ID,
            EMAIL,
            NAME,
            LASTNAME,
            PASSWORD,
            ROLES
        );
    }

    public static UserRegisterInput createUserRegisterInput() {
        return new UserRegisterInput(
            NAME,
            LASTNAME,
            EMAIL,
            PASSWORD
        );
    }

    public static Role createRole() throws NoSuchFieldException, IllegalAccessException {
        Role role = new Role();
        role.setName(ROLE_NAME);

        Field idField = BaseModel.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(role, RoleTestContant.ID);
        return role;
    }
}
