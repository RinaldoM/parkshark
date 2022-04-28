package com.switchfully.sharkitects.members;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "POSTALCODE_CITY")
public class PostalCodeCity {

    @Id
    @SequenceGenerator(name = "postalcode_city_seq", sequenceName = "postalcode_city_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "postalcode_city_seq")
    private Long id;

    @Column(name = "ZIPCODE")
    private String zipCode;
    @Column(name = "CITY")
    private String city;

    public PostalCodeCity(String zipCode, String city) {
        this.zipCode = zipCode;
        this.city = city;
    }

    public PostalCodeCity() {

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
