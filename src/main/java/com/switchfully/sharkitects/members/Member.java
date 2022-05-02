package com.switchfully.sharkitects.members;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "MEMBER")
public class Member {

    @Id
    private String id;

    @Column(name = "FIRSTNAME")
    private String firstName;
    @Column(name = "LASTNAME")
    private String lastName;
    @Embedded
    private Address address;
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
    @Column(name = "EMAIL")
    private String email;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_LICENSE_PLATE_ID")
    private LicensePlate licensePlate;
    @Column(name = "REGISTRATION_DATE", columnDefinition = "TIMESTAMP")
    private LocalDateTime registrationDate;

    @ManyToOne
    @JoinColumn(name = "FK_MEMBERSHIP_LEVEL_ID")
    private MembershipLevel membershipLevel;


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


    public Member() {
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
    public MembershipLevel getMembershipLevel() {
        return membershipLevel;
    }

    public void setMembershipLevel(MembershipLevel membershipLevel) {
        this.membershipLevel = membershipLevel;
    }
}
