package com.codenbugs.ms_user.services;

import com.codenbugs.ms_user.dtos.UserReponseDto;
import com.codenbugs.ms_user.dtos.UserRequestDto;
import com.codenbugs.ms_user.exceptions.UserNotCreatedException;
import com.codenbugs.ms_user.models.User;
import com.codenbugs.ms_user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

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

}
