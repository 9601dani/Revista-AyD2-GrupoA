package com.codenbugs.ms_user.service.User;

import static org.mockito.ArgumentMatchers.any;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.codenbugs.ms_user.dtos.User.LoginRequestDto;
import com.codenbugs.ms_user.dtos.User.UserReponseDto;
import com.codenbugs.ms_user.dtos.User.UserRequestDto;
import com.codenbugs.ms_user.exceptions.SettingNotFoundException;
import com.codenbugs.ms_user.exceptions.UserNotAllowedException;
import com.codenbugs.ms_user.exceptions.UserNotCreatedException;
import com.codenbugs.ms_user.exceptions.UserNotFoundException;
import com.codenbugs.ms_user.models.User.User;
import com.codenbugs.ms_user.repositories.User.UserRepository;
import com.codenbugs.ms_user.services.TokenService;
import com.codenbugs.ms_user.services.User.UserService;
import com.codenbugs.ms_user.services.User.UserServiceImpl;
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

    @Mock
    private TokenService tokenService;


    private UserRequestDto userRequestDto;
    private LoginRequestDto loginRequestDto;
    private User user;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl(userRepository, passwordEncoder, tokenService);

        userRequestDto = new UserRequestDto("test@codenbugs.com", "test", "password");

        loginRequestDto = new LoginRequestDto("test@codenbugs.com", "password");

        user = new User();
        user.setId(1);
        user.setEmail("test@codenbugs.com");
        user.setPassword(passwordEncoder.encode("password"));
        user.setUsername("test");

    }

    @Test
    public void registerUserSuccessfully() throws UserNotCreatedException {

        when(this.userRepository.save(any(User.class))).thenReturn(user);

        UserReponseDto expect = new UserReponseDto(user);

        UserReponseDto actual = userService.register(userRequestDto);

        assertEquals(expect, actual);
    }

    @Test
    public void registerUserEmailDuplicated() throws UserNotCreatedException {

        when(this.userRepository.findByEmail(userRequestDto.email())).thenReturn(Optional.of(user));

        assertThrows(UserNotCreatedException.class, () -> userService.register(userRequestDto));
    }

    @Test
    public void registerUserUsernameDuplicated() throws UserNotCreatedException {

        when(this.userRepository.findByUsername(userRequestDto.username())).thenReturn(Optional.of(user));

        assertThrows(UserNotCreatedException.class, () -> userService.register(userRequestDto));
    }

    @Test
    public void loginUserSuccessfully() throws UserNotCreatedException, SettingNotFoundException, UserNotFoundException, UserNotAllowedException {

        when(this.userRepository.findByUsernameOrEmail(loginRequestDto.usernameOrEmail(), loginRequestDto.usernameOrEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password", "password")).thenReturn(true);

        when(this.userRepository.save(any(User.class))).thenReturn(user);

        UserReponseDto expect = new UserReponseDto(user);

        UserReponseDto actual = this.userService.login(this.loginRequestDto);

        assertEquals(expect, actual);
    }

    @Test
    public void loginUsernameEmailNotFound() {
        when(this.userRepository.findByUsernameOrEmail(loginRequestDto.usernameOrEmail(), loginRequestDto.usernameOrEmail())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> this.userService.login(this.loginRequestDto));
    }

    @Test
    public void loginPasswordDoesntMatched() {

        when(this.userRepository.findByUsernameOrEmail(loginRequestDto.usernameOrEmail(), loginRequestDto.usernameOrEmail())).thenReturn(Optional.of(user));

        when(passwordEncoder.matches("password", "password")).thenReturn(false);
        assertThrows(UserNotAllowedException.class, () -> this.userService.login(this.loginRequestDto));
    }

}
