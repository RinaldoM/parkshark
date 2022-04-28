package com.switchfully.sharkitects.parking_lot;

import com.switchfully.sharkitects.members.Address;

public class ContactPerson {

    private String firstName;
    private String lastName;
    private String mobilePhoneNumber;
    private String telephoneNumber;
    private String email;
    private Address address;

    public ContactPerson(String firstName, String lastName, String mobilePhoneNumber, String telephoneNumber, String email, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobilePhoneNumber = mobilePhoneNumber;
        this.telephoneNumber = telephoneNumber;
        this.email = email;
        this.address = address;
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
