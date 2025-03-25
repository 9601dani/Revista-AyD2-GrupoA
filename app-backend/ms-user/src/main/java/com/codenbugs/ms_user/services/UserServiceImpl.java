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
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Getter
@Setter
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;


    public UserReponseDto register(UserRequestDto userRequestDto) throws UserNotCreatedException {
        Optional<User> userExists = userRepository.findByEmail(userRequestDto.getEmail());
        if (userExists.isPresent()) {
            throw new UserNotCreatedException("El email esta duplicado");
        }

        userExists = userRepository.findByUsername(userRequestDto.getUsername());
        if (userExists.isPresent()) {
            throw new UserNotCreatedException("El username esta duplicado");
        }

        User newUser = new User();
        newUser.setUsername(userRequestDto.getUsername());
        newUser.setEmail(userRequestDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));

        User createdUser = userRepository.save(newUser);

        UserReponseDto userReponseDto = new UserReponseDto(createdUser);
        return userReponseDto;
    }

    @Override
    public UserReponseDto login(LoginRequestDto request) throws SettingNotFoundException, UserNotAllowedException, UserNotFoundException {

        Optional<User> userOptional = this.userRepository.findByUsernameOrEmail(request.getUsernameOrEmail(), request.getUsernameOrEmail());
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("El usuario no existe");
        }

        User user = userOptional.get();

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UserNotAllowedException("La constraseña es incorrecta");
        }

        String token = tokenService.getToken(user);
        user.setAuthToken(token);

        this.userRepository.save(user);
        return new UserReponseDto(user);

    }
}
