package com.switchfully.sharkitects.parking_lot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ParkingLotAlreadyExistsException extends RuntimeException {
    public ParkingLotAlreadyExistsException(){
        super("A parking lot with the same name already exists");
    }
}
