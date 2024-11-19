package com.user_messaging_system.user_service.api;

import com.user_messaging_system.core_library.response.SuccessResponse;
import com.user_messaging_system.user_service.api.input.UserRegisterInput;
import com.user_messaging_system.user_service.api.input.UserUpdateInput;
import com.user_messaging_system.user_service.api.output.UserGetCurrentOutput;
import com.user_messaging_system.user_service.api.output.UserGetOutput;
import com.user_messaging_system.user_service.api.output.UserUpdateOutput;
import com.user_messaging_system.user_service.api.output.UserRegisterOutput;
import com.user_messaging_system.user_service.common.SuccessResponseBuilder;
import com.user_messaging_system.user_service.mapper.UserMapper;
import com.user_messaging_system.user_service.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<UserGetOutput>> getById(@PathVariable(name = "id") String id) {
        logger.info("[START] Method getById called in UserController with parameters: id={}", id);

        SuccessResponse<UserGetOutput> response = SuccessResponseBuilder.buildSuccessResponse(
                UserMapper.INSTANCE.userDtoToUserGetOutput(userService.getUserById(id)),
                HttpStatus.OK
        );

        logger.info("[SUCCESS] Method getById in UserController completed successfully. Response: {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-email")
    public ResponseEntity<SuccessResponse<UserGetCurrentOutput>> getByEmail(@RequestParam String email){
        logger.info("[START] Method getByEmail called in UserController with parameters: email={}", email);

        SuccessResponse<UserGetCurrentOutput> response = SuccessResponseBuilder.buildSuccessResponse(
                UserMapper.INSTANCE.userDtoToUserOutput(userService.getUserByEmail(email)),
                HttpStatus.OK
        );

        logger.info("[SUCCESS] Method getByEmail in UserController completed successfully. Response: {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/currentUser")
    public ResponseEntity<SuccessResponse<UserGetCurrentOutput>> getCurrentUser(
        @RequestHeader("Authorization") String jwtToken
    ){
        logger.info("[START] Method getCurrentUser called in UserController with parameters: jwtToken={}", jwtToken);

        SuccessResponse<UserGetCurrentOutput> response = SuccessResponseBuilder.buildSuccessResponse(
                UserMapper.INSTANCE.userDtoToUserOutput(userService.getCurrentUser(jwtToken)),
                HttpStatus.OK
        );

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
                " jwtToken={}, senderId={}, receiverId={}", jwtToken, senderId, receiverId);

        SuccessResponse<List<UserGetOutput>> response = SuccessResponseBuilder.buildSuccessResponse(
                UserMapper.INSTANCE.userDtoListToUserGetOutputList(
                        userService.getSenderAndReceiverByIds(jwtToken, senderId, receiverId)
                ),
                HttpStatus.OK
        );

        logger.info("[SUCCESS] Method getSenderAndReceiverByIds in UserController completed successfully. Response: {}", response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<SuccessResponse<UserRegisterOutput>> register(@RequestBody UserRegisterInput registerInput){
        logger.info("[START] Method register called in UserController with parameters: registerInput={}", registerInput);

        SuccessResponse<UserRegisterOutput> response = SuccessResponseBuilder.buildSuccessResponse(
                userService.createUser(registerInput),
                HttpStatus.CREATED
        );

        logger.info("[SUCCESS] Method register in UserController completed successfully. Response: {}", response);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<SuccessResponse<UserUpdateOutput>> updateCurrentUser(
            @RequestHeader("Authorization") String jwtToken,
            @RequestBody UserUpdateInput userUpdateInput
    ){
        logger.info("[START] Method updateCurrentUser called in UserController with parameters: jwtToken={}, userUpdateInput={}", jwtToken, userUpdateInput);

        SuccessResponse<UserUpdateOutput> response = SuccessResponseBuilder.buildSuccessResponse(
                UserMapper.INSTANCE.userDtoToUserUpdateOutput(userService.updateCurrentUser(jwtToken, userUpdateInput)),
                HttpStatus.OK
        );

        logger.info("[SUCCESS] Method updateCurrentUser in UserController completed successfully. Response: {}", response);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCurrentUser(@RequestHeader("Authorization") String jwtToken){
        logger.info("[START] Method deleteCurrentUser called in UserController with parameters: jwtToken={}", jwtToken);

        userService.deleteCurrentUser(jwtToken);

        logger.info("[SUCCESS] Method deleteCurrentUser in UserController completed successfully.");
        return ResponseEntity.noContent().build();
    }
}
