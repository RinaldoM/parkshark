package com.switchfully.sharkitects.members.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MissingLicensePlateIssuingCountryException extends RuntimeException {
    public MissingLicensePlateIssuingCountryException() {
        super("No license plate issuing country has been provided during member registration.");
    }
}
