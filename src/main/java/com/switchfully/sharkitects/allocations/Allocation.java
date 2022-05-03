package com.switchfully.sharkitects.allocations;

import com.switchfully.sharkitects.members.Member;
import com.switchfully.sharkitects.parking_lot.ParkingLot;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="ALLOCATION")
public class Allocation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "allocation_seq")
    @SequenceGenerator(name = "allocation_seq", sequenceName = "allocation_seq", allocationSize = 1)
    private Integer id;

    @OneToOne
    @JoinColumn (name="FK_MEMBER_ID")
    private Member member;

    @Column(name="license_plate_number")
    private String licensePlateNumber;

    @ManyToOne
    @JoinColumn(name="FK_PARKING_LOT_ID")
    private ParkingLot parkingLot;

    @Column (name="START_DATE_TIME")
    private LocalDateTime allocationStartDateTime;

    public Allocation(Member member, String licensePlateNumber, ParkingLot parkingLot, LocalDateTime allocationStartDateTime) {
        this.member = member;
        this.licensePlateNumber = licensePlateNumber;
        this.parkingLot = parkingLot;
        this.allocationStartDateTime = allocationStartDateTime;
    }

    public Allocation() {
    }

    public Integer getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public LocalDateTime getAllocationStartDateTime() {
        return allocationStartDateTime;
    }
}
