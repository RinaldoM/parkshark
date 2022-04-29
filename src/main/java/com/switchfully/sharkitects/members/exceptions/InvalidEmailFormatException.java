package com.switchfully.sharkitects.members.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidEmailFormatException extends RuntimeException {
    public InvalidEmailFormatException() {
        super("An invalid email address format has been provided during member registration.");
    }
}
