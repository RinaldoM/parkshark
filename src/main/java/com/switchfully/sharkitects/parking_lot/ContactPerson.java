package com.switchfully.sharkitects.parking_lot;

import com.switchfully.sharkitects.members.Address;

import javax.persistence.*;
import java.util.UUID;


@Entity
@Table(name="CONTACT_PERSON")
public class ContactPerson {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contact_person_seq")
    @SequenceGenerator(name = "contact_person_seq", sequenceName = "contact_person_seq", allocationSize = 1)
    private Long  id;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "MOBILE_PHONE_NUMBER")
    private String mobilePhoneNumber;
    @Column(name = "TELEPHONE_NUMBER")
    private String telephoneNumber;
    @Column(name = "EMAIL")
    private String email;
    @Embedded
    private Address address;

    public ContactPerson(String firstName, String lastName, String mobilePhoneNumber, String telephoneNumber, String email, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobilePhoneNumber = mobilePhoneNumber;
        this.telephoneNumber = telephoneNumber;
        this.email = email;
        this.address = address;
    }

    public ContactPerson() {

    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }
}
