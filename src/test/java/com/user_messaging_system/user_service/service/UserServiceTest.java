package com.user_messaging_system.user_service.service;

import com.user_messaging_system.core_library.exception.UserNotFoundException;
import com.user_messaging_system.core_library.service.JWTService;
import com.user_messaging_system.user_service.dto.UserDTO;
import com.user_messaging_system.user_service.mapper.UserMapper;
import com.user_messaging_system.user_service.model.User;
import com.user_messaging_system.user_service.repository.UserRepository;
import com.user_messaging_system.user_service.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import static com.user_messaging_system.user_service.util.UserServiceUtil.*;
import static com.user_messaging_system.user_service.constant.UserTestConstant.*;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private JWTService jwtService;

    @InjectMocks
    private UserServiceImpl userService;

    @Nested
    class GetTest{
        //givenExistUserId_whenGetUserById_thenReturnUserDTO
        //givenNotExistUserId_whenGetUserById_thenThrowUserNotFoundException
        //givenNullUserId_whenGetUserById_thenThrowIllegalArgumentException
        //givenEmptyUserId_whenGetUserById_thenThrowIllegalArgumentException
        //givenInvalidFormatUserId_whenGetUserById_thenThrowIllegalArgumentException

        @Test
        void givenExistUserId_whenGetUserById_thenReturnUserDTO() throws NoSuchFieldException, IllegalAccessException {
            String userId = ID;
            User user = createUser();
            UserDTO expectedUserDTO = createUserDTO();

            when(userRepository.findById(userId)).thenReturn(Optional.of(user));
            when(userMapper.userToUserDTO(user)).thenReturn(expectedUserDTO);

            UserDTO actualUserDTO = userService.getUserById(userId);

            assertNotNull(actualUserDTO);
            assertEquals(expectedUserDTO, actualUserDTO);

            verify(userRepository, times(1)).findById(userId);
            verify(userMapper, times(1)).userToUserDTO(user);
        }

        @Test
        void givenNotExistUserId_whenGetUserById_thenThrowUserNotFoundException(){
            String userId = ID;

            when(userRepository.findById(userId)).thenReturn(Optional.empty());

            assertThrows(UserNotFoundException.class, () -> userService.getUserById(userId));

            verify(userRepository, times(1)).findById(userId);
            verify(userMapper, never()).userToUserDTO(any());
        }

        @Test
        void givenNullUserId_whenGetUserById_thenThrowIllegalArgumentException(){
            assertThrows(IllegalArgumentException.class, () -> userService.getUserById(null));

            verify(userRepository, never()).findById(any());
            verify(userMapper, never()).userToUserDTO(any());
        }

        @Test
        void givenEmptyUserId_whenGetUserById_thenThrowIllegalArgumentException(){
            String userId = "";

            assertThrows(IllegalArgumentException.class, () -> userService.getUserById(userId));

            verify(userRepository, never()).findById(any());
            verify(userMapper, never()).userToUserDTO(any());
        }

        @Test
        void givenInvalidFormatUserId_whenGetUserById_thenThrowIllegalArgumentException(){
            String userId = "invalid-format_id";

            assertThrows(IllegalArgumentException.class, () -> userService.getUserById(userId));

            verify(userRepository, never()).findById(any());
            verify(userMapper, never()).userToUserDTO(any());
        }

        //givenExistEmail_whenGetUserByEmail_thenReturnUserDTO
        //givenNotExistEmail_whenGetUserByEmail_thenThrowUserNotFoundException
        //givenNullEmail_whenGetUserByEmail_thenThrowIllegalArgumentException
        //givenEmptyEmptyId_whenGetUserByEmail_thenThrowIllegalArgumentException
        //givenInvalidFormatEmail_whenGetUserByEmail_thenThrowIllegalArgumentException

        @Test
        void givenExistEmail_whenGetUserByEmail_thenReturnUserDTO() throws NoSuchFieldException, IllegalAccessException {
            String email = EMAIL;
            User user = createUser();
            UserDTO expectedUserDTO = createUserDTO();

            when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
            when(userMapper.userToUserDTO(user)).thenReturn(expectedUserDTO);

            UserDTO actualUserDTO = userService.getUserByEmail(email);

            assertNotNull(actualUserDTO);
            assertEquals(expectedUserDTO, actualUserDTO);

            verify(userRepository, times(1)).findByEmail(email);
            verify(userMapper, times(1)).userToUserDTO(user);
        }

        @Test
        void givenNotExistEmail_whenGetUserByEmail_thenThrowUserNotFoundException() {
            String email = NOT_EXIST_EMAIL;

            when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

            assertThrows(UserNotFoundException.class, () -> userService.getUserByEmail(email));

            verify(userRepository, times(1)).findByEmail(email);
            verify(userMapper, never()).userToUserDTO(any());
        }

        @Test
        void givenNullEmail_whenGetUserByEmail_thenThrowIllegalArgumentException(){
            assertThrows(IllegalArgumentException.class, () -> userService.getUserByEmail(null));

            verify(userRepository, never()).findByEmail(any());
            verify(userMapper, never()).userToUserDTO(any());
        }

        @Test
        void givenEmptyEmail_whenGetUserByEmail_thenThrowIllegalArgumentException(){
            assertThrows(IllegalArgumentException.class, () -> userService.getUserByEmail(""));

            verify(userRepository, never()).findByEmail(any());
            verify(userMapper, never()).userToUserDTO(any());
        }

        @Test
        void givenInvalidFormatEmail_whenGetUserByEmail_thenThrowIllegalArgumentException(){
            assertThrows(IllegalArgumentException.class, () -> userService.getUserByEmail(INVALID_FORMAT_EMAIL));

            verify(userRepository, never()).findByEmail(any());
            verify(userMapper, never()).userToUserDTO(any());
        }

        //givenExistToken_whenGetCurrentUser_thenReturnUserDTO
        //givenNotExistToken_whenGetCurrentUser_thenThrowIllegalArgumentException
        //

        @Test
        void givenExistToken_whenGetCurrentUser_thenReturnUserDTO() throws NoSuchFieldException, IllegalAccessException {
            String token = TOKEN;
            String email = EMAIL;
            User user = createUser();
            UserDTO expectedUserDTO = createUserDTO();

            when(jwtService.extractToken(token)).thenReturn(token);
            doNothing().when(jwtService).validateToken(token);
            when(jwtService.extractEmail(token)).thenReturn(email);
            when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
            when(userMapper.userToUserDTO(user)).thenReturn(expectedUserDTO);

            UserDTO actualUserDTO = userService.getCurrentUser(token);

            assertNotNull(actualUserDTO);
            assertEquals(expectedUserDTO, actualUserDTO);

            verify(userRepository, times(1)).findByEmail(email);
            verify(userMapper, times(1)).userToUserDTO(user);
            verify(jwtService, times(1)).extractToken(token);
            verify(jwtService, times(1)).validateToken(token);
            verify(jwtService, times(1)).extractEmail(token);
        }
    }

    @Nested
    class CreateTest{

    }

    @Nested
    class UpdateTest{

    }

    @Nested
    class DeleteTest{

    }
}
