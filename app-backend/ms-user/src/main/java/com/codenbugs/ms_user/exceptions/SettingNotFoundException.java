package com.codenbugs.ms_user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SettingNotFoundException extends Exception {
    public SettingNotFoundException(String message) {
        super(message);
    }
}
