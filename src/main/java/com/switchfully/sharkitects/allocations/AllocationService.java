package com.switchfully.sharkitects.allocations;

import com.switchfully.sharkitects.allocations.dtos.AllocatedSpotDto;
import com.switchfully.sharkitects.allocations.dtos.StartAllocatingParkingSpotDto;
import com.switchfully.sharkitects.allocations.exceptions.IdDoesNotExist;
import com.switchfully.sharkitects.infrastructure.Infrastructure;
import com.switchfully.sharkitects.members.Member;
import com.switchfully.sharkitects.members.MemberService;
import com.switchfully.sharkitects.parking_lot.ParkingLot;
import com.switchfully.sharkitects.parking_lot.ParkingLotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AllocationService {

    private final MemberService memberService;
    private final ParkingLotService parkingLotService;
    private final AllocationRepository allocationRepository;
    private final AllocationMapper allocationMapper;
    private final Logger logger = LoggerFactory.getLogger(AllocationService.class);

    public AllocationService(MemberService memberService, ParkingLotService parkingLotService, AllocationRepository allocationRepository, AllocationMapper allocationMapper) {
        this.memberService = memberService;
        this.parkingLotService = parkingLotService;
        this.allocationRepository = allocationRepository;
        this.allocationMapper = allocationMapper;
    }

    public AllocatedSpotDto startAllocatingParkingSpot(StartAllocatingParkingSpotDto startAllocatingParkingSpotDto) {
        logger.info("Attempting to allocate a parking spot");

        checkProvidedInput(startAllocatingParkingSpotDto);


        Member member = memberService.findById(startAllocatingParkingSpotDto.getMemberId());
        ParkingLot parkingLot = parkingLotService.findById(startAllocatingParkingSpotDto.getParkingLotId());
        Allocation allocation = allocationMapper.toAllocation(startAllocatingParkingSpotDto, member, parkingLot);
        AllocatedSpotDto allocatedSpotDto = allocationMapper.toAllocatedSpotDto(allocationRepository.save(allocation));
        logger.info("Parking spot allocated");
        return allocatedSpotDto;
    }

    private void checkProvidedInput(StartAllocatingParkingSpotDto startAllocatingParkingSpotDto) {
        Infrastructure.inputValidation(memberService.noMemberWithThisIdExists(startAllocatingParkingSpotDto.getMemberId()), new IdDoesNotExist("member"));
        Infrastructure.inputValidation(parkingLotService.noParkingLotWithThisIdExists(startAllocatingParkingSpotDto.getParkingLotId()), new IdDoesNotExist("parking lot"));
    }


}
