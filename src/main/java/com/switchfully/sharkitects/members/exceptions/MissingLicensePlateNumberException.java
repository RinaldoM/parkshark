package com.switchfully.sharkitects.members.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MissingLicensePlateNumberException extends RuntimeException {
    public MissingLicensePlateNumberException() {
        super("No license plate number has been provided during member registration.");
    }
}
