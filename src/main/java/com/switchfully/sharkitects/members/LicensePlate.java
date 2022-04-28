package com.switchfully.sharkitects.members;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "LICENSE_PLATE")
public class LicensePlate {

    @Id
    @SequenceGenerator(name = "license_plate_seq", sequenceName = "license_plate_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "license_plate_seq")
    private Long id;

    @Column(name = "NUMBER")
    private String number;
    @Column(name = "ISSUING_COUNTRY")
    private String issuingCountry;

    public LicensePlate(String number, String issuingCountry) {
        this.number = number;
        this.issuingCountry = issuingCountry;
    }

    public LicensePlate() {

    }

    public String getNumber() {
        return number;
    }

    public String getIssuingCountry() {
        return issuingCountry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LicensePlate that = (LicensePlate) o;
        return Objects.equals(number, that.number) && Objects.equals(issuingCountry, that.issuingCountry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, issuingCountry);
    }
}
