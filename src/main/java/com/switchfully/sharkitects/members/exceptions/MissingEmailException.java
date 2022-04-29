package com.switchfully.sharkitects.members.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MissingEmailException extends RuntimeException {
    public MissingEmailException() {
        super("No email address has been provided during member registration.");
    }
}
