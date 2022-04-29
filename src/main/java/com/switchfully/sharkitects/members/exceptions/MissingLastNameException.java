package com.switchfully.sharkitects.members.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MissingLastNameException extends RuntimeException {
    public MissingLastNameException() {
        super("No last name has been provided during member registration.");
    }
}
