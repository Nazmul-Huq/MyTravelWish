package com.nazmul.mytravelwish;

public class Wish {
    private String place;
    private String country;

    public Wish() {
    }

    public Wish(String place, String country) {
        this.place = place;
        this.country = country;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
