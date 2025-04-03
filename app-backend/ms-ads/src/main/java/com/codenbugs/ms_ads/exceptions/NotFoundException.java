package com.codenbugs.ms_ads.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends AdException {

    public NotFoundException(String message) {
        super(message);
    }
}
