package com.switchfully.sharkitects.members;

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
}
