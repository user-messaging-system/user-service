package com.user_messaging_system.user_service.common.constant;

public class ApiConstant {
    private ApiConstant(){
        throw new AssertionError("This class should not be instantiated");
    }

    public static final String API_VERSION = "/v1/api/users";


    public static final String USER_SUCCESSFULLY_REGISTERED_MESSAGE = "User registered successfully";
    public static final String USER_SUCCESSFULLY_RETRIEVED_MESSAGE = "User retrieved successfully";
    public static final String USER_SUCCESSFULLY_UPDATED_MESSAGE = "User updated successfully";
    public static final String USER_SUCCESSFULLY_DELETED_MESSAGE = "User deleted successfully";
}
