package com.switchfully.sharkitects.members;

public class PostalCodeCity {
    private String zipCode;
    private String city;

    public PostalCodeCity(String zipCode, String city) {
        this.zipCode = zipCode;
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCity() {
        return city;
    }
}
