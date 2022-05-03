package com.switchfully.sharkitects.allocations.dtos;

import java.time.LocalDateTime;

public class AllocatedSpotDto {
    private final Integer id;
    private final String memberId;
    private final String licensePlate;
    private final Integer parkingLotId;
    private final LocalDateTime allocationStartDateTime;

    public AllocatedSpotDto(Integer id, String memberId, String licensePlate, Integer parkingLotId, LocalDateTime allocationStartDateTime) {
        this.id = id;
        this.memberId = memberId;
        this.licensePlate = licensePlate;
        this.parkingLotId = parkingLotId;
        this.allocationStartDateTime = allocationStartDateTime;
    }

    public Integer getId() {
        return id;
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
