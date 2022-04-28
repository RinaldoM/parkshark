package com.switchfully.sharkitects.members;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostalCodeCity that = (PostalCodeCity) o;
        return Objects.equals(zipCode, that.zipCode) && Objects.equals(city, that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zipCode, city);
    }
}
