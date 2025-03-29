package com.codenbugs.gateway.exceptions.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NotAllowedException extends UserException {
    public NotAllowedException(String message) {
        super(message);
    }
}
