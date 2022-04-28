package com.switchfully.sharkitects.parking_lot.dto;

import com.switchfully.sharkitects.members.Address;
import com.switchfully.sharkitects.parking_lot.Category;
import com.switchfully.sharkitects.parking_lot.ContactPerson;

public class CreateParkingLotDto {
    private String name;
    private Category category;
    private int maxCapacity;
    private Address address;
    private ContactPerson contactPerson;
    private int pricePerHour;

    public CreateParkingLotDto(String name, Category category, int maxCapacity, Address address, ContactPerson contactPerson, int pricePerHour) {
        this.name = name;
        this.category = category;
        this.maxCapacity = maxCapacity;
        this.address = address;
        this.contactPerson = contactPerson;
        this.pricePerHour = pricePerHour;
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
