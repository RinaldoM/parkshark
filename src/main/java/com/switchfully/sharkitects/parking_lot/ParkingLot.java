package com.switchfully.sharkitects.parking_lot;

import com.switchfully.sharkitects.members.Address;

import javax.persistence.*;

@Entity
@Table (name = "parking_lot")
public class ParkingLot {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parking_lot_seq")
    @SequenceGenerator(name = "parking_lot_seq", sequenceName = "parking_lot_seq", allocationSize = 1)
    private Long id;
    @Column (name = "NAME")
    private String name;
    @Enumerated(EnumType.STRING)
    @Column (name = "CATEGORY")
    private Category category;
    @Column (name = "MAX_CAPACITY")
    private int maxCapacity;
    @Embedded
    private Address address;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="FK_CONTACT_PERSON_ID")
    private ContactPerson contactPerson;
    @Column (name = "PRICE_PER_HOUR")
    private double pricePerHour;

    public ParkingLot(String name, Category category, int maxCapacity, Address address, ContactPerson contactPerson, double pricePerHour) {
        this.name = name;
        this.category = category;
        this.maxCapacity = maxCapacity;
        this.address = address;
        this.contactPerson = contactPerson;
        this.pricePerHour = pricePerHour;
    }

    public ParkingLot() {

    }

    public Long getId() {
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

    public double getPricePerHour() {
        return pricePerHour;
    }
}
