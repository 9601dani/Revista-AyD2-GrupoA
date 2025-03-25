package com.codenbugs.ms_user.services.User;

import com.codenbugs.ms_user.dtos.user.LoginRequestDto;
import com.codenbugs.ms_user.dtos.user.UserReponseDto;
import com.codenbugs.ms_user.dtos.user.UserRequestDto;
import com.codenbugs.ms_user.exceptions.SettingNotFoundException;
import com.codenbugs.ms_user.exceptions.UserNotAllowedException;
import com.codenbugs.ms_user.exceptions.UserNotCreatedException;
import com.codenbugs.ms_user.exceptions.UserNotFoundException;


public interface UserService {

    UserReponseDto register(UserRequestDto userRequestDto) throws UserNotCreatedException;

    UserReponseDto login(LoginRequestDto request) throws UserNotCreatedException, SettingNotFoundException, UserNotAllowedException, UserNotFoundException;

}
