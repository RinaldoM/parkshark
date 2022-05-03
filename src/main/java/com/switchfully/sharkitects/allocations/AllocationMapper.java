package com.switchfully.sharkitects.allocations;

import com.switchfully.sharkitects.allocations.dtos.AllocatedSpotDto;
import com.switchfully.sharkitects.allocations.dtos.StartAllocatingParkingSpotDto;
import com.switchfully.sharkitects.members.Member;
import com.switchfully.sharkitects.parking_lot.ParkingLot;
import org.springframework.stereotype.Component;

@Component
public class AllocationMapper {
    public Allocation toAllocation(StartAllocatingParkingSpotDto startAllocatingParkingSpotDto, Member member, ParkingLot parkingLot) {
        return new Allocation(member,
                startAllocatingParkingSpotDto.getLicensePlateNumber(),
                parkingLot,
                startAllocatingParkingSpotDto.getAllocationStartDateTime());

    }

    public AllocatedSpotDto toAllocatedSpotDto(Allocation allocation) {
        return new AllocatedSpotDto(allocation.getId(),
                allocation.getMember().getId(),
                allocation.getLicensePlateNumber(),
                allocation.getParkingLot().getId(),
                allocation.getAllocationStartDateTime());
    }
}
