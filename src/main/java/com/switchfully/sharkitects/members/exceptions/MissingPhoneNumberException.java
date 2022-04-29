package com.switchfully.sharkitects.members.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MissingPhoneNumberException extends RuntimeException {
    public MissingPhoneNumberException() {
        super("No phone number has been provided during member registration.");
    }
}
