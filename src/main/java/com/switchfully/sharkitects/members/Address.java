package com.switchfully.sharkitects.members;

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
}
