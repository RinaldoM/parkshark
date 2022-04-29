package com.switchfully.sharkitects.parking_lot.dto;

import com.switchfully.sharkitects.parking_lot.ParkingLot;
import com.switchfully.sharkitects.parking_lot.ParkingLotDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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

    public ParkingLotDto toParkingLotDto(ParkingLot parkingLot){


        return new ParkingLotDto(
                parkingLot.getId(),
                parkingLot.getName(),
                parkingLot.getMaxCapacity(),
                parkingLot.getContactPerson().getEmail(),
                parkingLot.getContactPerson().getMobilePhoneNumber(),
                parkingLot.getContactPerson().getTelephoneNumber()
        );
    }

    public List<ParkingLotDto> toParkingLotDto(List<ParkingLot> allParkingLots) {
        return allParkingLots.stream()
                .map(this::toParkingLotDto)
                .collect(Collectors.toList());
    }
}
