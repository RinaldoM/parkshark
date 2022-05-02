package com.switchfully.sharkitects.members.dtos;

import com.switchfully.sharkitects.members.Address;
import com.switchfully.sharkitects.members.LicensePlate;

import java.time.LocalDateTime;

public class MemberDto {
    private String id;
    private String firstName;
    private String lastName;
    private Address address;
    private String phoneNumber;
    private String email;
    private LicensePlate licensePlate;
    private LocalDateTime registrationDate;
    private String membershipLevelName;

    public MemberDto(String id, String firstName, String lastName, Address address, String phoneNumber, String email, LicensePlate licensePlate, LocalDateTime registrationDate, String membershipLevelName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.licensePlate = licensePlate;
        this.registrationDate = registrationDate;
        this.membershipLevelName = membershipLevelName;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Address getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public LicensePlate getLicensePlate() {
        return licensePlate;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public String getMembershipLevelName() {
        return membershipLevelName;
    }
}
