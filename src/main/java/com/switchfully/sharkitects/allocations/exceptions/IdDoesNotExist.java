package com.switchfully.sharkitects.allocations.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IdDoesNotExist extends RuntimeException {
    public IdDoesNotExist(String missingItem) {
        super("No " + missingItem + " with this ID exists");
    }
}
