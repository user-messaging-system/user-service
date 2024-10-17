package com.user_messaging_system.user_service.api;

import com.user_messaging_system.core_library.response.SuccessResponse;
import com.user_messaging_system.user_service.api.input.UserInput;
import com.user_messaging_system.user_service.api.output.UserCreateOutput;
import com.user_messaging_system.user_service.api.output.UserOutput;
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

    @GetMapping("/{senderId}/{receiverId}")
    public ResponseEntity<SuccessResponse<List<UserOutput>>> getSenderAndReceiverByIds(
            @PathVariable String senderId,
            @PathVariable String receiverId
    ){
        SuccessResponse<List<UserOutput>> response = SuccessResponseBuilder.buildSuccessResponse(
                UserMapper.INSTANCE.userDtoListToUserOutputList(userService.getSenderAndReceiverByIds(senderId, receiverId)),
                HttpStatus.OK
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<UserOutput>> getById(@PathVariable(name = "id") String id) {
        SuccessResponse<UserOutput> response = SuccessResponseBuilder.buildSuccessResponse(
                        UserMapper.INSTANCE.userDtoToUserOutput(userService.getUserById(id)),
                        HttpStatus.OK
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-email")
    public ResponseEntity<SuccessResponse<UserOutput>> getByEmail(@RequestParam String email){
        SuccessResponse<UserOutput> response = SuccessResponseBuilder.buildSuccessResponse(
                UserMapper.INSTANCE.userDtoToUserOutput(userService.getUserByEmail(email)),
                HttpStatus.OK
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<SuccessResponse<UserCreateOutput>> register(@RequestBody UserInput userInput){
        SuccessResponse<UserCreateOutput> response = SuccessResponseBuilder.buildSuccessResponse(
                userService.createUser(userInput),
                HttpStatus.CREATED
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable(name = "id") String id) {
        return ResponseEntity.ok(null);
    }
}