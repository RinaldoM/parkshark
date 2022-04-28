package com.switchfully.sharkitects.members;

import java.time.LocalDateTime;
import java.util.UUID;

public class Member {

    private final String id;
    private final String firstName;
    private final String lastName;
    private final Address address;
    private final String phoneNumber;
    private final String email;
    private final LicensePlate licensePlate;
    private final LocalDateTime registrationDate;

    public Member(String firstName, String lastName, Address address, String phoneNumber, String email, LicensePlate licensePlate, LocalDateTime registrationDate) {
        this.id = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.licensePlate = licensePlate;
        this.registrationDate = registrationDate;
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
}
