package com.switchfully.sharkitects.members.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MissingStreetNameException extends RuntimeException {
    public MissingStreetNameException() {
        super("No street name has been provided during member registration.");
    }
}
