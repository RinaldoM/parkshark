package com.switchfully.sharkitects.parking_lot.dto;

public class ParkingLotDto {

    private final Integer id;
    private final String name;
    private final int maxCapacity;
    private final String contactPersonEmail;
    private final String contactPersonTelephoneNumber;
    private final String contactPersonMobilePhoneNumber;

    public ParkingLotDto(Integer id, String name, int maxCapacity, String contactPersonEmail, String mobile, String telephone) {
        this.id = id;
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.contactPersonEmail = contactPersonEmail;
        this.contactPersonTelephoneNumber = telephone;
        this.contactPersonMobilePhoneNumber = mobile;
    }


    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public String getContactPersonEmail() {
        return contactPersonEmail;
    }

    public String getContactPersonTelephoneNumber() {
        return contactPersonTelephoneNumber;
    }

    public String getContactPersonMobilePhoneNumber() {
        return contactPersonMobilePhoneNumber;
    }
}
