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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<UserGetOutput>> getById(@PathVariable(name = "id") String id) {
        SuccessResponse<UserGetOutput> response = SuccessResponseBuilder.buildSuccessResponse(
                UserMapper.INSTANCE.userDtoToUserGetOutput(userService.getUserById(id)),
                HttpStatus.OK
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-email")
    public ResponseEntity<SuccessResponse<UserGetCurrentOutput>> getByEmail(@RequestParam String email){
        SuccessResponse<UserGetCurrentOutput> response = SuccessResponseBuilder.buildSuccessResponse(
                UserMapper.INSTANCE.userDtoToUserOutput(userService.getUserByEmail(email)),
                HttpStatus.OK
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/currentUser")
    public ResponseEntity<SuccessResponse<UserGetCurrentOutput>> getCurrentUser(
            @RequestHeader("Authorization") String jwtToken
    ){
        SuccessResponse<UserGetCurrentOutput> response = SuccessResponseBuilder.buildSuccessResponse(
                UserMapper.INSTANCE.userDtoToUserOutput(userService.getCurrentUser(jwtToken)),
                HttpStatus.OK
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{senderId}/{receiverId}")
    public ResponseEntity<SuccessResponse<List<UserGetOutput>>> getSenderAndReceiverByIds(
            @RequestHeader("Authorization") String jwtToken,
            @PathVariable String senderId,
            @PathVariable String receiverId
    ){
        SuccessResponse<List<UserGetOutput>> response = SuccessResponseBuilder.buildSuccessResponse(
                UserMapper.INSTANCE.userDtoListToUserGetOutputList(
                        userService.getSenderAndReceiverByIds(jwtToken, senderId, receiverId)
                ),
                HttpStatus.OK
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<SuccessResponse<UserRegisterOutput>> register(@RequestBody UserRegisterInput registerInput){
        SuccessResponse<UserRegisterOutput> response = SuccessResponseBuilder.buildSuccessResponse(
                userService.createUser(registerInput),
                HttpStatus.CREATED
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<SuccessResponse<UserUpdateOutput>> updateCurrentUser(
            @RequestHeader("Authorization") String jwtToken,
            @RequestBody UserUpdateInput userUpdateInput
    ){
        SuccessResponse<UserUpdateOutput> response = SuccessResponseBuilder.buildSuccessResponse(
                UserMapper.INSTANCE.userDtoToUserUpdateOutput(userService.updateCurrentUser(jwtToken, userUpdateInput)),
                HttpStatus.OK
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCurrentUser(@RequestHeader("Authorization") String jwtToken){
        userService.deleteCurrentUser(jwtToken);
        return ResponseEntity.noContent().build();
    }
}