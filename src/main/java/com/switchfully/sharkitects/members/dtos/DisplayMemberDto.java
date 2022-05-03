package com.switchfully.sharkitects.members.dtos;



public class DisplayMemberDto {
    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String licensePlate;
    private String registrationDate;
    private String membershipLevelName;

    public DisplayMemberDto(String id, String firstName, String lastName, String phoneNumber, String email, String licensePlate, String registrationDate, String membershipLevelName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
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

    public String getMembershipLevelName() {
        return membershipLevelName;
    }
}
