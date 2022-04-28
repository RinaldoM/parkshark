package com.switchfully.sharkitects.parking_lot;

import com.switchfully.sharkitects.parking_lot.dto.CreateParkingLotDto;
import com.switchfully.sharkitects.parking_lot.dto.ParkingLotMapper;
import org.springframework.stereotype.Service;

@Service
public class ParkingLotService {


    private final ParkingLotMapper parkingLotMapper;
    private final ParkingLotRepository parkingLotRepository;

    public ParkingLotService(ParkingLotMapper parkingLotMapper, ParkingLotRepository parkingLotRepository) {
        this.parkingLotMapper = parkingLotMapper;
        this.parkingLotRepository = parkingLotRepository;
    }

    public CreateParkingLotDto createParkingLot(CreateParkingLotDto createParkingLotDto) {
        parkingLotRepository.save(parkingLotMapper.toParkingLot(createParkingLotDto));
        return createParkingLotDto;
    }
}
