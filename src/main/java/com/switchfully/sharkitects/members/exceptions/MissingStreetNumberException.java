package com.switchfully.sharkitects.members.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MissingStreetNumberException extends RuntimeException {
    public MissingStreetNumberException() {
        super("No street number has been provided during member registration.");
    }
}
