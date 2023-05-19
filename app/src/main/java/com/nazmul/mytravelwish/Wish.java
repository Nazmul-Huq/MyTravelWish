package com.nazmul.mytravelwish;

public class Wish {
    private String destination;
    private String note;

    private String city;

    public Wish() {
    }

    public Wish(String destination, String note, String city) {
        this.destination = destination;
        this.note = note;
        this.city = city;
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
