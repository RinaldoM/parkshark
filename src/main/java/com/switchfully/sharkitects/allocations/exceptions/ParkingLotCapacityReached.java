package com.switchfully.sharkitects.allocations.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ParkingLotCapacityReached extends RuntimeException {
    public ParkingLotCapacityReached() {
        super("The parking lot is full");
    }
}
