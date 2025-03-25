package com.codenbugs.ms_user.services;

import com.codenbugs.ms_user.dtos.LoginRequestDto;
import com.codenbugs.ms_user.dtos.UserReponseDto;
import com.codenbugs.ms_user.dtos.UserRequestDto;
import com.codenbugs.ms_user.exceptions.SettingNotFoundException;
import com.codenbugs.ms_user.exceptions.UserNotAllowedException;
import com.codenbugs.ms_user.exceptions.UserNotCreatedException;
import com.codenbugs.ms_user.exceptions.UserNotFoundException;
import com.codenbugs.ms_user.models.User;
import com.codenbugs.ms_user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface UserService {

    UserReponseDto register(UserRequestDto userRequestDto) throws UserNotCreatedException;

    UserReponseDto login(LoginRequestDto request) throws UserNotCreatedException, SettingNotFoundException, UserNotAllowedException, UserNotFoundException;

}
