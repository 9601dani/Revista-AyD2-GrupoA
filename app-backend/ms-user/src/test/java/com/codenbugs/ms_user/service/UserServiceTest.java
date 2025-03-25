package com.codenbugs.ms_user.service;

import static org.mockito.ArgumentMatchers.any;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.codenbugs.ms_user.dtos.UserReponseDto;
import com.codenbugs.ms_user.dtos.UserRequestDto;
import com.codenbugs.ms_user.exceptions.UserNotCreatedException;
import com.codenbugs.ms_user.models.User;
import com.codenbugs.ms_user.repositories.UserRepository;
import com.codenbugs.ms_user.services.UserService;
import com.codenbugs.ms_user.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;


    UserRequestDto userRequestDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl(userRepository, passwordEncoder);

        userRequestDto = new UserRequestDto();
        userRequestDto.setEmail("test@codenbugs.com");
        userRequestDto.setPassword("password");
        userRequestDto.setUsername("test");
    }

    @Test
    public void registerUserSuccessfully() throws UserNotCreatedException {

        User user = new User();
        user.setId(1);
        user.setEmail("test@codenbugs.com");
        user.setPassword(passwordEncoder.encode("password"));
        user.setUsername("test");

        when(this.userRepository.save(any(User.class))).thenReturn(user);

        UserReponseDto expect = new UserReponseDto(user);

        UserReponseDto actual = userService.register(userRequestDto);

        assertEquals(expect, actual);
    }

    @Test
    public void registerUserEmailDuplicated() throws UserNotCreatedException {

        User user = new User();
        user.setId(1);
        user.setEmail("test@codenbugs.com");
        user.setPassword(passwordEncoder.encode("password"));
        user.setUsername("test");

        when(this.userRepository.findByEmail(userRequestDto.getEmail())).thenReturn(Optional.of(user));

        assertThrows(UserNotCreatedException.class, () -> userService.register(userRequestDto));
    }

    @Test
    public void registerUserUsernameDuplicated() throws UserNotCreatedException {

        User user = new User();
        user.setId(1);
        user.setEmail("test@codenbugs.com");
        user.setPassword(passwordEncoder.encode("password"));
        user.setUsername("test");

        when(this.userRepository.findByUsername(userRequestDto.getUsername())).thenReturn(Optional.of(user));

        assertThrows(UserNotCreatedException.class, () -> userService.register(userRequestDto));
    }

}
