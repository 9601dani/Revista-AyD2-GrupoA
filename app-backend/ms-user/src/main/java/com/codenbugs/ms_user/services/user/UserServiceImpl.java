package com.codenbugs.ms_user.services.user;

import com.codenbugs.ms_user.dtos.user.LoginRequestDto;
import com.codenbugs.ms_user.dtos.user.UserReponseDto;
import com.codenbugs.ms_user.dtos.user.UserRequestDto;
import com.codenbugs.ms_user.exceptions.SettingNotFoundException;
import com.codenbugs.ms_user.exceptions.UserNotAllowedException;
import com.codenbugs.ms_user.exceptions.UserNotCreatedException;
import com.codenbugs.ms_user.exceptions.UserNotFoundException;
import com.codenbugs.ms_user.models.user.User;
import com.codenbugs.ms_user.repositories.user.UserRepository;
import com.codenbugs.ms_user.services.TokenService;
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
        Optional<User> userExists = userRepository.findByEmail(userRequestDto.email());
        if (userExists.isPresent()) {
            throw new UserNotCreatedException("El email esta duplicado");
        }

        userExists = userRepository.findByUsername(userRequestDto.username());
        if (userExists.isPresent()) {
            throw new UserNotCreatedException("El username esta duplicado");
        }

        User newUser = new User();
        newUser.setUsername(userRequestDto.username());
        newUser.setEmail(userRequestDto.email());
        newUser.setPassword(passwordEncoder.encode(userRequestDto.password()));

        User createdUser = userRepository.save(newUser);

        return new UserReponseDto(createdUser);
    }

    @Override
    public UserReponseDto login(LoginRequestDto request) throws SettingNotFoundException, UserNotAllowedException, UserNotFoundException {

        Optional<User> userOptional = this.userRepository.findByUsernameOrEmail(request.usernameOrEmail(), request.usernameOrEmail());
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("El usuario no existe");
        }

        User user = userOptional.get();

        if(!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new UserNotAllowedException("La constraseña es incorrecta");
        }

        String token = tokenService.getToken(user);
        user.setAuthToken(token);

        this.userRepository.save(user);
        return new UserReponseDto(user);

    }
}
