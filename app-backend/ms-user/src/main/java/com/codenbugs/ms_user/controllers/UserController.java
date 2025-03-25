package com.codenbugs.ms_user.controllers;

import com.codenbugs.ms_user.dtos.LoginRequestDto;
import com.codenbugs.ms_user.dtos.UserReponseDto;
import com.codenbugs.ms_user.dtos.UserRequestDto;
import com.codenbugs.ms_user.exceptions.SettingNotFoundException;
import com.codenbugs.ms_user.exceptions.UserNotAllowedException;
import com.codenbugs.ms_user.exceptions.UserNotCreatedException;
import com.codenbugs.ms_user.exceptions.UserNotFoundException;
import com.codenbugs.ms_user.services.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("")
    public String helloWorld() {
        return "Hello world!";
    }

    @PostMapping("/register")
    public ResponseEntity<UserReponseDto> saveDriver(@RequestBody UserRequestDto request) throws UserNotCreatedException {
        UserReponseDto userReponseDto = this.userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(userReponseDto);
    }

    @PostMapping("/login")
    @Transactional
    public ResponseEntity<UserReponseDto> login(@RequestBody LoginRequestDto request) throws UserNotFoundException, SettingNotFoundException, UserNotAllowedException, UserNotCreatedException {
        UserReponseDto response = this.userService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
