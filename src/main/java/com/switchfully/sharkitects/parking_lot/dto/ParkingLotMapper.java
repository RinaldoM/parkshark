package com.switchfully.sharkitects.parking_lot.dto;

import com.switchfully.sharkitects.parking_lot.CreateParkingDto;
import org.springframework.stereotype.Component;

@Component
public class ParkingLotMapper {
    public CreateParkingDto toParkingLot(CreateParkingLotDto createParkingLotDto){
        return new CreateParkingDto(
                createParkingLotDto.getName(),
                createParkingLotDto.getCategory(),
                createParkingLotDto.getMaxCapacity(),
                createParkingLotDto.getAddress(),
                createParkingLotDto.getContactPerson(),
                createParkingLotDto.getPricePerHour()
        );
    }
}
