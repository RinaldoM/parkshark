package com.switchfully.sharkitects.allocations.dtos;

import java.time.LocalDateTime;

public class StartAllocatingParkingSpotDto {
    private final String memberId;
    private final String licensePlate;
    private final Integer parkingLotId;
    private final LocalDateTime allocationStartDateTime;

    public StartAllocatingParkingSpotDto(String memberId, String licensePlate, Integer parkingLotId) {
        this.memberId = memberId;
        this.licensePlate = licensePlate;
        this.parkingLotId = parkingLotId;
        this.allocationStartDateTime = LocalDateTime.now();
    }

    public String getMemberId() {
        return memberId;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public Integer getParkingLotId() {
        return parkingLotId;
    }

    public LocalDateTime getAllocationStartDateTime() {
        return allocationStartDateTime;
    }
}
