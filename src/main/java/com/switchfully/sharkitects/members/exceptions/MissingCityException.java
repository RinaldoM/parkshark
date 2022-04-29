package com.switchfully.sharkitects.members.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MissingCityException extends RuntimeException {
    public MissingCityException() {
        super("No city has been provided during member registration.");
    }
}
