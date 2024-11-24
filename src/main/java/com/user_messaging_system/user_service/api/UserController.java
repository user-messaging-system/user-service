package com.user_messaging_system.user_service.api;

import com.user_messaging_system.core_library.response.SuccessResponse;
import com.user_messaging_system.user_service.api.input.UserRegisterInput;
import com.user_messaging_system.user_service.api.input.UserUpdateInput;
import com.user_messaging_system.user_service.api.output.UserGetCurrentOutput;
import com.user_messaging_system.user_service.api.output.UserGetOutput;
import com.user_messaging_system.user_service.api.output.UserUpdateOutput;
import com.user_messaging_system.user_service.api.output.UserRegisterOutput;
import com.user_messaging_system.user_service.common.constant.ApiConstant;
import com.user_messaging_system.user_service.mapper.UserMapper;
import com.user_messaging_system.user_service.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.user_messaging_system.user_service.common.constant.ApiConstant.*;

@RestController
@RequestMapping(ApiConstant.API_VERSION)
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<UserGetOutput>> getById(@PathVariable(name = "id") String id) {
        logger.info("[START] Method getById called in UserController with parameters: id={}", id);
        SuccessResponse<UserGetOutput> response = new SuccessResponse.Builder<UserGetOutput>()
                .message(USER_SUCCESSFULLY_RETRIEVED_MESSAGE)
                .data(UserMapper.INSTANCE.userDtoToUserGetOutput(userService.getUserById(id)))
                .status(HttpStatus.OK.toString())
                .build();

        logger.info("[SUCCESS] Method getById in UserController completed successfully. Response: {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-email")
    public ResponseEntity<SuccessResponse<UserGetCurrentOutput>> getByEmail(@RequestParam String email){
        logger.info("[START] Method getByEmail called in UserController with parameters: email={}", email);
        SuccessResponse<UserGetCurrentOutput> response = new SuccessResponse.Builder<UserGetCurrentOutput>()
                .message(USER_SUCCESSFULLY_RETRIEVED_MESSAGE)
                .data(UserMapper.INSTANCE.userDtoToUserOutput(userService.getUserByEmail(email)))
                .status(HttpStatus.OK.toString())
                .build();

        logger.info("[SUCCESS] Method getByEmail in UserController completed successfully. Response: {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<SuccessResponse<UserGetCurrentOutput>> getCurrentUser(
        @RequestHeader("Authorization") String jwtToken
    ){
        logger.info("[START] Method getCurrentUser called in UserController with parameters: jwtToken={}", jwtToken);
        SuccessResponse<UserGetCurrentOutput> response = new SuccessResponse.Builder<UserGetCurrentOutput>()
                .message(USER_SUCCESSFULLY_RETRIEVED_MESSAGE)
                .data(UserMapper.INSTANCE.userDtoToUserOutput(userService.getCurrentUser(jwtToken)))
                .status(HttpStatus.OK.toString())
                .build();

        logger.info("[SUCCESS] Method getCurrentUser in UserController completed successfully. Response: {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{senderId}/{receiverId}")
    public ResponseEntity<SuccessResponse<List<UserGetOutput>>> getSenderAndReceiverByIds(
            @RequestHeader("Authorization") String jwtToken,
            @PathVariable String senderId,
            @PathVariable String receiverId
    ){
        logger.info("[START] Method getSenderAndReceiverByIds called in UserController with parameters:" +
                " userId={}, senderId={}, receiverId={}", jwtToken, senderId, receiverId);
        SuccessResponse<List<UserGetOutput>> response = new SuccessResponse.Builder<List<UserGetOutput>>()
                .message(USER_SUCCESSFULLY_RETRIEVED_MESSAGE)
                .data(UserMapper.INSTANCE.userDtoListToUserGetOutputList(
                        userService.getSenderAndReceiverByIds(jwtToken, senderId, receiverId)
                ))
                .status(HttpStatus.OK.toString())
                .build();

        logger.info(
                "[SUCCESS] Method getSenderAndReceiverByIds in UserController completed successfully. Response: {}",
                response
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<SuccessResponse<UserRegisterOutput>> createUser(@RequestBody UserRegisterInput registerInput){
        logger.info("[START] Method register called in UserController with parameters: registerInput={}", registerInput);
        SuccessResponse<UserRegisterOutput> response = new SuccessResponse.Builder<UserRegisterOutput>()
                .message(USER_SUCCESSFULLY_REGISTERED_MESSAGE)
                .data(UserMapper.INSTANCE.userRegisterDTOToUserRegisterOutput(userService.createUser(registerInput)))
                .status(HttpStatus.CREATED.toString())
                .build();

        logger.info("[SUCCESS] Method register in UserController completed successfully. Response: {}", response);
        return ResponseEntity.ok(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<SuccessResponse<UserUpdateOutput>> updateUser(
            @PathVariable String id,
            @RequestHeader("Authorization") String jwtToken,
            @RequestBody UserUpdateInput userUpdateInput
    ){
        logger.info(
                "[START] Method updateCurrentUser called in UserController with parameters: jwtToken={}, userUpdateInput={}",
                jwtToken,
                userUpdateInput
        );
        SuccessResponse<UserUpdateOutput> response = new SuccessResponse.Builder<UserUpdateOutput>()
                .message(USER_SUCCESSFULLY_UPDATED_MESSAGE)
                .data(UserMapper.INSTANCE.userDtoToUserUpdateOutput(userService.updateUserById(id, jwtToken, userUpdateInput)))
                .status(HttpStatus.OK.toString())
                .build();

        logger.info("[SUCCESS] Method updateCurrentUser in UserController completed successfully. Response: {}", response);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable String id,
            @RequestHeader("Authorization") String jwtToken
    ){
        logger.info("[START] Method deleteCurrentUser called in UserController with parameters: jwtToken={}", jwtToken);
        userService.deleteUser(id, jwtToken);
        logger.info("[SUCCESS] Method deleteCurrentUser in UserController completed successfully.");
        return ResponseEntity.noContent().build();
    }
}
