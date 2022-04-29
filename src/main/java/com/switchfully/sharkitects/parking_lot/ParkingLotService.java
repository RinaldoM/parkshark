package com.switchfully.sharkitects.parking_lot;

import com.switchfully.sharkitects.infrastructure.EmptyInputException;
import com.switchfully.sharkitects.infrastructure.Infrastructure;
import com.switchfully.sharkitects.parking_lot.dto.CreateParkingLotDto;
import com.switchfully.sharkitects.parking_lot.dto.ParkingLotMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class ParkingLotService {

    private final ParkingLotMapper parkingLotMapper;
    private final ParkingLotRepository parkingLotRepository;
    private final Logger serviceLogger = LoggerFactory.getLogger(ParkingLotService.class);


    public ParkingLotService(ParkingLotMapper parkingLotMapper, ParkingLotRepository parkingLotRepository) {
        this.parkingLotMapper = parkingLotMapper;
        this.parkingLotRepository = parkingLotRepository;
    }

    public CreateParkingLotDto createParkingLot(CreateParkingLotDto createParkingLotDto) {
        serviceLogger.info("Attempting to create a new parking lot");
        checkEachInputField(createParkingLotDto);

        parkingLotRepository.save(parkingLotMapper.toParkingLot(createParkingLotDto));
        serviceLogger.info("parking lot created");
        return createParkingLotDto;
    }

    private void checkEachInputField(CreateParkingLotDto createParkingLotDto) {
        Infrastructure.inputValidation(Infrastructure.isNullEmptyOrBlank(createParkingLotDto.getName()), new EmptyInputException("Parking name"));
        Infrastructure.inputValidation(Infrastructure.isNullEmptyOrBlank(createParkingLotDto.getMaxCapacity()), new EmptyInputException("Maximum capacity"));
        Infrastructure.inputValidation(Infrastructure.isNullEmptyOrBlank(createParkingLotDto.getAddress().getStreetName()), new EmptyInputException("Street name"));
        Infrastructure.inputValidation(Infrastructure.isNullEmptyOrBlank(createParkingLotDto.getAddress().getStreetNumber()), new EmptyInputException("Street number"));
        Infrastructure.inputValidation(Infrastructure.isNullEmptyOrBlank(createParkingLotDto.getAddress().getPostalCodeCity().getZipCode()), new EmptyInputException("Zip code"));
        Infrastructure.inputValidation(Infrastructure.isNullEmptyOrBlank(createParkingLotDto.getAddress().getPostalCodeCity().getCity()), new EmptyInputException("City"));
        Infrastructure.inputValidation(Infrastructure.isNullEmptyOrBlank(createParkingLotDto.getPricePerHour()), new EmptyInputException("Price per hour"));
        // Checks for contact person:
        Infrastructure.inputValidation(Infrastructure.isNullEmptyOrBlank(createParkingLotDto.getContactPerson().getFirstName()), new EmptyInputException("First name"));
        Infrastructure.inputValidation(Infrastructure.isNullEmptyOrBlank(createParkingLotDto.getContactPerson().getLastName()), new EmptyInputException("Last name"));
        Infrastructure.inputValidation(Infrastructure.isNullEmptyOrBlank(createParkingLotDto.getContactPerson().getEmail()), new EmptyInputException("Email address"));

        Infrastructure.inputValidation(checkIfNoPhoneNumberProvided(createParkingLotDto), new EmptyInputException("mobile phone number or telephone number"));


    }

    private boolean checkIfNoPhoneNumberProvided(CreateParkingLotDto createParkingLotDto) {
        return Infrastructure.isNullEmptyOrBlank(createParkingLotDto.getContactPerson().getMobilePhoneNumber())
                && Infrastructure.isNullEmptyOrBlank(createParkingLotDto.getContactPerson().getTelephoneNumber());
    }
}
