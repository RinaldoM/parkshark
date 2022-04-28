package com.switchfully.sharkitects.members;

import java.util.Objects;

public class Address {
    private String streetName;
    private String streetNumber;
    private PostalCodeCity postalCodeCity;

    public Address(String streetName, String streetNumber, PostalCodeCity postalCodeCity) {
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.postalCodeCity = postalCodeCity;
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
