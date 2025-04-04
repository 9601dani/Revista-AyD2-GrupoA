package com.codenbugs.ms_user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserNotCreatedException extends Exception {

    public UserNotCreatedException(String message) {
        super(message);
    }

}
