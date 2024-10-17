package com.user_messaging_system.user_service.api.output;

import java.util.List;

public interface UserBaseOutput {
    String email();
    String name();
    String lastName();
    List<String> roles();
}
