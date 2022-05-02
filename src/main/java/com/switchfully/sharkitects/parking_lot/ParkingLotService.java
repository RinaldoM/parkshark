package com.switchfully.sharkitects.parking_lot;

import com.switchfully.sharkitects.infrastructure.exceptions.EmptyInputException;
import com.switchfully.sharkitects.infrastructure.Infrastructure;
import com.switchfully.sharkitects.infrastructure.exceptions.InvalidEmailFormatException;
import com.switchfully.sharkitects.infrastructure.exceptions.InvalidNumberException;
import com.switchfully.sharkitects.parking_lot.dto.CreateParkingLotDto;
import com.switchfully.sharkitects.parking_lot.dto.ParkingLotDto;
import com.switchfully.sharkitects.parking_lot.exceptions.ParkingLotAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


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

    private boolean parkingLotWithSameNameAlreadyExists(CreateParkingLotDto createParkingLotDto) {
        List<ParkingLot> parkingLotsWithSameName = parkingLotRepository.findByName(createParkingLotDto.getName()).stream()
                .filter(parkingLot -> parkingLot.getName().equals(createParkingLotDto.getName()))
                .toList();
        return parkingLotsWithSameName.size() > 0;
    }

    private void checkEachInputField(CreateParkingLotDto createParkingLotDto) {
        Infrastructure.inputValidation(Infrastructure.isNullEmptyOrBlank(createParkingLotDto.getName()), new EmptyInputException("parking name"));
        Infrastructure.inputValidation(Infrastructure.isLessOrEqualTo0(createParkingLotDto.getMaxCapacity()), new InvalidNumberException("max capacity"));
        Infrastructure.inputValidation(Infrastructure.isNullEmptyOrBlank(createParkingLotDto.getAddress().getStreetName()), new EmptyInputException("street name"));
        Infrastructure.inputValidation(Infrastructure.isNullEmptyOrBlank(createParkingLotDto.getAddress().getStreetNumber()), new EmptyInputException("street number"));
        Infrastructure.inputValidation(Infrastructure.isNullEmptyOrBlank(createParkingLotDto.getAddress().getPostalCodeCity().getZipCode()), new EmptyInputException("zip code"));
        Infrastructure.inputValidation(Infrastructure.isNullEmptyOrBlank(createParkingLotDto.getAddress().getPostalCodeCity().getCity()), new EmptyInputException("city"));
        Infrastructure.inputValidation(Infrastructure.isLessOrEqualTo0(createParkingLotDto.getPricePerHour()), new InvalidNumberException("price per hour"));
        // Checks for contact person:
        Infrastructure.inputValidation(Infrastructure.isNullEmptyOrBlank(createParkingLotDto.getContactPerson().getFirstName()), new EmptyInputException("first name"));
        Infrastructure.inputValidation(Infrastructure.isNullEmptyOrBlank(createParkingLotDto.getContactPerson().getLastName()), new EmptyInputException("last name"));
        Infrastructure.inputValidation(Infrastructure.isNullEmptyOrBlank(createParkingLotDto.getContactPerson().getEmail()), new EmptyInputException("email address"));
        Infrastructure.inputValidation(Infrastructure.isEmailFormatIncorrect(createParkingLotDto.getContactPerson().getEmail()), new InvalidEmailFormatException());

        Infrastructure.inputValidation(checkIfNoPhoneNumberProvided(createParkingLotDto), new EmptyInputException("mobile phone number and telephone number"));

        Infrastructure.inputValidation((parkingLotWithSameNameAlreadyExists(createParkingLotDto)), new ParkingLotAlreadyExistsException());
    }

    private boolean checkIfNoPhoneNumberProvided(CreateParkingLotDto createParkingLotDto) {
        return Infrastructure.isNullEmptyOrBlank(createParkingLotDto.getContactPerson().getMobilePhoneNumber())
                && Infrastructure.isNullEmptyOrBlank(createParkingLotDto.getContactPerson().getTelephoneNumber());
    }

    public List<ParkingLotDto> getAllParkingLots() {
        serviceLogger.info("All parking lots are displayed.");
        return parkingLotMapper.toParkingLotDto(parkingLotRepository.findAll());
    }
}
