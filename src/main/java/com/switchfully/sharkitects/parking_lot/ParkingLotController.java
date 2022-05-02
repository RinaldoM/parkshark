package com.switchfully.sharkitects.parking_lot;

import com.switchfully.sharkitects.parking_lot.dto.CreateParkingLotDto;
import com.switchfully.sharkitects.parking_lot.dto.ParkingLotDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/parking-lots")
public class ParkingLotController {

    private final ParkingLotService parkingLotService;

    public ParkingLotController(ParkingLotService parkingLotService) {
        this.parkingLotService = parkingLotService;
    }

    @PreAuthorize("hasAuthority('CREATE_PARKING_LOT')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CreateParkingLotDto createParkingLot(@RequestBody CreateParkingLotDto createParkingLotDto) {
        return parkingLotService.createParkingLot(createParkingLotDto);
    }

    @PreAuthorize("hasAuthority('VIEW_ALL_PARKING_LOTS')")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ParkingLotDto> getAllMembers(){
        return parkingLotService.getAllParkingLots();
    }
}
