package com.switchfully.sharkitects.members;

import javax.persistence.*;
import java.util.Objects;

@Embeddable
public class Address {

    @Column(name = "STREET_NAME")
    private String streetName;
    @Column(name = "STREET_NUMBER")
    private String streetNumber;
    @ManyToOne(cascade = CascadeType.ALL) // should be many to one
    @JoinColumn(name = "FK_POSTALCODE_CITY_ID")
    private PostalCodeCity postalCodeCity;

    public Address(String streetName, String streetNumber, PostalCodeCity postalCodeCity) {
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.postalCodeCity = postalCodeCity;
    }

    public Address() {

    }

    public String getStreetName() {
        return streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public PostalCodeCity getPostalCodeCity() {
        return postalCodeCity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(streetName, address.streetName) && Objects.equals(streetNumber, address.streetNumber) && Objects.equals(postalCodeCity, address.postalCodeCity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(streetName, streetNumber, postalCodeCity);
    }
}
