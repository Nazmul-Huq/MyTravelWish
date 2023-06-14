package com.nazmul.mytravelwish;

import android.graphics.Bitmap;
import android.net.Uri;

public class Wish {
    private String id;
    private String destination;
    private String note;
    private String city;
    private String country;

    public Wish() {
    }

    public Wish(String id, String destination, String note, String city, String country) {
        this.id = id;
        this.destination = destination;
        this.note = note;
        this.city = city;
        this.country = country;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
