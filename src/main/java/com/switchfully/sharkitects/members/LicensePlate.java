package com.switchfully.sharkitects.members;

import java.util.Objects;

public class LicensePlate {

    private String number;
    private String issuingCountry;

    public LicensePlate(String number, String issuingCountry) {
        this.number = number;
        this.issuingCountry = issuingCountry;
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
