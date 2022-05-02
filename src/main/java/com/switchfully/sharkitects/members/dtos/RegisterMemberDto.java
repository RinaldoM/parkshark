package com.switchfully.sharkitects.members.dtos;

import com.switchfully.sharkitects.members.Address;
import com.switchfully.sharkitects.members.LicensePlate;

import java.time.LocalDateTime;

public class RegisterMemberDto {
    private final String firstName;
    private final String lastName;
    private final Address address;
    private final String phoneNumber;
    private final String email;
    private final LicensePlate licensePlate;
    private final LocalDateTime registrationDate;
    private String membershipLevel;

    public RegisterMemberDto(String firstName, String lastName, Address address, String phoneNumber, String email, LicensePlate licensePlate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.licensePlate = licensePlate;
        this.registrationDate = LocalDateTime.now();
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

    public String getMembershipLevel() {
        return membershipLevel;
    }
}
