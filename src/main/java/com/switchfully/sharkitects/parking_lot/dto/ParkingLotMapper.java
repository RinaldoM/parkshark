package com.switchfully.sharkitects.parking_lot.dto;

import com.switchfully.sharkitects.parking_lot.ParkingLot;
import org.springframework.stereotype.Component;

@Component
public class ParkingLotMapper {
    public ParkingLot toParkingLot(CreateParkingLotDto createParkingLotDto){
        return new ParkingLot(
                createParkingLotDto.getName(),
                createParkingLotDto.getCategory(),
                createParkingLotDto.getMaxCapacity(),
                createParkingLotDto.getAddress(),
                createParkingLotDto.getContactPerson(),
                createParkingLotDto.getPricePerHour()
        );
    }
}
