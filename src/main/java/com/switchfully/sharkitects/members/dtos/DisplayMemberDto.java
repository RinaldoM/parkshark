package com.switchfully.sharkitects.members.dtos;



public class DisplayMemberDto {
    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String licensePlate;
    private String registrationDate;

    public DisplayMemberDto(String id, String firstName, String lastName, String phoneNumber, String email, String licensePlate, String registrationDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }
}
