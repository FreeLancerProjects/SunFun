package com.creativeshare.sunfun.models;

import java.io.Serializable;

public class SelectedLocation implements Serializable {

    private String address;
    private double lat;
    private double lng;

    public SelectedLocation(String address, double lat, double lng) {
        this.address = address;
        this.lat = lat;
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}
