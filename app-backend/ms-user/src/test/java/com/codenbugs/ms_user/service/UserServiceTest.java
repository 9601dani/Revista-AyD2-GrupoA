package com.codenbugs.ms_user.service;

import static org.mockito.ArgumentMatchers.any;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.codenbugs.ms_user.dtos.LoginRequestDto;
import com.codenbugs.ms_user.dtos.UserReponseDto;
import com.codenbugs.ms_user.dtos.UserRequestDto;
import com.codenbugs.ms_user.exceptions.SettingNotFoundException;
import com.codenbugs.ms_user.exceptions.UserNotAllowedException;
import com.codenbugs.ms_user.exceptions.UserNotCreatedException;
import com.codenbugs.ms_user.exceptions.UserNotFoundException;
import com.codenbugs.ms_user.models.User;
import com.codenbugs.ms_user.repositories.UserRepository;
import com.codenbugs.ms_user.services.TokenService;
import com.codenbugs.ms_user.services.UserService;
import com.codenbugs.ms_user.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenService tokenService;


    UserRequestDto userRequestDto;
    LoginRequestDto loginRequestDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl(userRepository, passwordEncoder, tokenService);

        userRequestDto = new UserRequestDto();
        userRequestDto.setEmail("test@codenbugs.com");
        userRequestDto.setPassword("password");
        userRequestDto.setUsername("test");

        loginRequestDto = new LoginRequestDto();
        loginRequestDto.setPassword("test@codenbugs.com");
        loginRequestDto.setPassword("password");
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

    @Test
    public void loginUserSuccessfully() throws UserNotCreatedException, SettingNotFoundException, UserNotFoundException, UserNotAllowedException {

        User user = new User();
        user.setId(1);
        user.setEmail("test@codenbugs.com");
        user.setUsername("test");
        user.setPassword("password");

        when(this.userRepository.findByUsernameOrEmail(loginRequestDto.getUsernameOrEmail(),loginRequestDto.getUsernameOrEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password", "password")).thenReturn(true);

        when(this.userRepository.save(any(User.class))).thenReturn(user);

        UserReponseDto expect = new UserReponseDto(user);

        UserReponseDto actual = this.userService.login(this.loginRequestDto);

        assertEquals(expect, actual);
    }

    @Test
    public void loginUsernameEmailNotFound()  {



        when(this.userRepository.findByUsernameOrEmail(loginRequestDto.getUsernameOrEmail(),loginRequestDto.getUsernameOrEmail())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> this.userService.login(this.loginRequestDto));
    }

    @Test
    public void loginPasswordDoesntMatched()  {

        User user = new User();
        user.setId(1);
        user.setEmail("test@codenbugs.com");
        user.setUsername("test");
        user.setPassword("password");

        when(this.userRepository.findByUsernameOrEmail(loginRequestDto.getUsernameOrEmail(),loginRequestDto.getUsernameOrEmail())).thenReturn(Optional.of(user));

        when(passwordEncoder.matches("password", "password")).thenReturn(false);
        assertThrows(UserNotAllowedException.class, () -> this.userService.login(this.loginRequestDto));
    }

}
