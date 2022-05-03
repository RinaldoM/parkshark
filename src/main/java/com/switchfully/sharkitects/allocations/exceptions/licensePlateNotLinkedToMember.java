package com.switchfully.sharkitects.allocations.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class licensePlateNotLinkedToMember extends RuntimeException {

    public licensePlateNotLinkedToMember(String message) {
        super("The provided license plate number is not linked to the provided member id");
    }
}
