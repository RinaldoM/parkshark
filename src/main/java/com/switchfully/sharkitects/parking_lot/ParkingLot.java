package com.switchfully.sharkitects.parking_lot;

import com.switchfully.sharkitects.members.Address;

import javax.persistence.*;
import java.util.UUID;

//@Entity
//@Table (name = "parking_lot")
public class ParkingLot {

//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parking_lot_seq")
//    @SequenceGenerator(name = "parking_lot_seq", sequenceName = "parking_lot_seq", allocationSize = 1)
    private String id;
//    @Column (name = "NAME")
    private String name;
    private Category category;
    private int maxCapacity;
    private Address address;
    private ContactPerson contactPerson;
    private int pricePerHour;


    public ParkingLot(String name, Category category, int maxCapacity, Address address, ContactPerson contactPerson, int pricePerHour) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.category = category;
        this.maxCapacity = maxCapacity;
        this.address = address;
        this.contactPerson = contactPerson;
        this.pricePerHour = pricePerHour;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public Address getAddress() {
        return address;
    }

    public ContactPerson getContactPerson() {
        return contactPerson;
    }

    public int getPricePerHour() {
        return pricePerHour;
    }
}
