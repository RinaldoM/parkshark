package com.switchfully.sharkitects.members.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MissingZipCodeException extends RuntimeException {
    public MissingZipCodeException() {
        super("No zip code has been provided during member registration.");
    }
}
