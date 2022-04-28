package com.switchfully.sharkitects.parking_lot;

import com.switchfully.sharkitects.parking_lot.dto.CreateParkingLotDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/parking-lots")
public class ParkingLotController {
//
//    private ParkingLotRepository parkingLotRepository;

//    public ParkingLotController(ParkingLotRepository parkingLotRepository) {
//        this.parkingLotRepository = parkingLotRepository;
//    }

    private final ParkingLotService parkingLotService;

    public ParkingLotController(ParkingLotService parkingLotService) {
        this.parkingLotService = parkingLotService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CreateParkingLotDto createParkingLot(@RequestBody CreateParkingLotDto createParkingLotDto) {
        return parkingLotService.createParkingLot(createParkingLotDto);
    }
}
