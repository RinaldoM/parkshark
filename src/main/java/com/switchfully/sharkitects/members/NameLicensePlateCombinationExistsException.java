package com.switchfully.sharkitects.members;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NameLicensePlateCombinationExistsException extends RuntimeException {
    public NameLicensePlateCombinationExistsException() {
        super("A member with the same name and the same license plate already exists.");
    }
}
