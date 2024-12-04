package com.user_messaging_system.user_service.api;

import com.user_messaging_system.core_library.common.annotation.LogExecution;
import com.user_messaging_system.core_library.common.constant.ValidationConstant;
import com.user_messaging_system.core_library.response.SuccessResponse;
import com.user_messaging_system.user_service.api.input.UserRegisterInput;
import com.user_messaging_system.user_service.api.input.UserUpdateInput;
import com.user_messaging_system.user_service.api.output.*;
import com.user_messaging_system.user_service.mapper.UserMapper;
import com.user_messaging_system.user_service.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static com.user_messaging_system.core_library.common.constant.APIConstant.*;
import static com.user_messaging_system.core_library.common.constant.MessageConstant.*;
import static com.user_messaging_system.core_library.common.constant.ValidationConstant.*;

@RestController
@RequestMapping(USER_SERVICE_BASE_URL)
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @LogExecution
    @GetMapping("{id}")
    public ResponseEntity<SuccessResponse<UserGetOutput>> getById(
            @Valid
            @Size(min = UUID_LENGTH, max = UUID_LENGTH, message = ValidationConstant.INVALID_USER_ID)
            @PathVariable(name = "id") String id
    ) {
        SuccessResponse<UserGetOutput> response = new SuccessResponse.Builder<UserGetOutput>()
                .message(USER_SUCCESSFULLY_RETRIEVED)
                .data(userMapper.userDtoToUserGetOutput(userService.getUserById(id)))
                .status(HttpStatus.OK.toString())
                .build();
        return ResponseEntity.ok(response);
    }

    @LogExecution
    @GetMapping("/by-email")
    public ResponseEntity<SuccessResponse<UserAuthenticationOutput>> getByEmail(
            @Valid
            @Email(message = INVALID_EMAIL)
            @RequestParam(name = "email") String email
    ) {
        SuccessResponse<UserAuthenticationOutput> response = new SuccessResponse.Builder<UserAuthenticationOutput>()
                .message(USER_SUCCESSFULLY_RETRIEVED)
                .data(userMapper.userDtoToUserAuthenticationOutput(userService.getUserByEmail(email)))
                .status(HttpStatus.OK.toString())
                .build();
        return ResponseEntity.ok(response);
    }

    @LogExecution
    @GetMapping(CURRENT_USER_PATH)
    public ResponseEntity<SuccessResponse<UserGetCurrentOutput>> getCurrentUser(
            @CookieValue("accessToken") String jwtToken
    ){
        SuccessResponse<UserGetCurrentOutput> response = new SuccessResponse.Builder<UserGetCurrentOutput>()
                .message(USER_SUCCESSFULLY_RETRIEVED)
                .data(userMapper.userDtoToUserOutput(userService.getCurrentUser(jwtToken)))
                .status(HttpStatus.OK.toString())
                .build();
        return ResponseEntity.ok(response);
    }

    @LogExecution
    @GetMapping(PAIR_PATH)
    public ResponseEntity<SuccessResponse<List<UserGetOutput>>> getSenderAndReceiverByIds(
            @RequestHeader("Authorization") String jwtToken,
            @Valid @Size(min = UUID_LENGTH, max = UUID_LENGTH, message = INVALID_SENDER_ID) @RequestParam String senderId,
            @Valid @Size(min = UUID_LENGTH, max = UUID_LENGTH, message = INVALID_RECEIVER_ID) @RequestParam String receiverId
    ){
        SuccessResponse<List<UserGetOutput>> response = new SuccessResponse.Builder<List<UserGetOutput>>()
                .message(USERS_SUCCESSFULLY_RETRIEVED)
                .data(userMapper.userDtoListToUserGetOutputList(
                        userService.getSenderAndReceiverByIds(jwtToken, senderId, receiverId)
                ))
                .status(HttpStatus.OK.toString())
                .build();
        return ResponseEntity.ok(response);
    }

    @LogExecution
    @PostMapping
    public ResponseEntity<SuccessResponse<UserRegisterOutput>> createUser(
            @Valid @RequestBody UserRegisterInput userRegisterInput
    ){
        SuccessResponse<UserRegisterOutput> response = new SuccessResponse.Builder<UserRegisterOutput>()
                .message(USER_SUCCESSFULLY_REGISTERED)
                .data(userMapper.userRegisterDTOToUserRegisterOutput(userService.createUser(userRegisterInput)))
                .status(HttpStatus.CREATED.toString())
                .build();
        return ResponseEntity.ok(response);
    }

    @LogExecution
    @PutMapping("{id}")
    public ResponseEntity<SuccessResponse<UserUpdateOutput>> updateUser(
            @Valid
            @Size(min = UUID_LENGTH, max = UUID_LENGTH, message = ValidationConstant.INVALID_USER_ID)
            @PathVariable String id,
            @RequestHeader("Authorization") String jwtToken,
            @RequestBody UserUpdateInput userUpdateInput
    ){
        SuccessResponse<UserUpdateOutput> response = new SuccessResponse.Builder<UserUpdateOutput>()
                .message(USER_SUCCESSFULLY_UPDATED)
                .data(userMapper.userDtoToUserUpdateOutput(userService.updateUserById(id, jwtToken, userUpdateInput)))
                .status(HttpStatus.OK.toString())
                .build();
        return ResponseEntity.ok(response);
    }

    @LogExecution
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(
            @Valid
            @Size(min = UUID_LENGTH, max = UUID_LENGTH, message = ValidationConstant.INVALID_USER_ID)
            @PathVariable String id,
            @RequestHeader("Authorization") String jwtToken
    ){
        userService.deleteUser(id, jwtToken);
        return ResponseEntity.noContent().build();
    }
}
