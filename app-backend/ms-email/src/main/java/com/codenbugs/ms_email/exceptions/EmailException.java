package com.codenbugs.ms_email.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmailException extends Exception{

    public EmailException(String message) {
        super(message);
    }
}
