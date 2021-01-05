package com.mickwerf.digi_tours_breda.live_data.route_logic.ors.models;

import java.io.Serializable;

public class Coordinate implements Serializable {

    private double longitude;
    private double latitude;

    public Coordinate(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
