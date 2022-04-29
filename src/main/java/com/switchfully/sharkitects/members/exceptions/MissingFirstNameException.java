package com.switchfully.sharkitects.members.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MissingFirstNameException extends RuntimeException {
    public MissingFirstNameException() {
        super("No first name has been provided during member registration.");
    }
}
