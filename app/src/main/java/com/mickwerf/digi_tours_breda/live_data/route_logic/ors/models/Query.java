package com.mickwerf.digi_tours_breda.live_data.route_logic.ors.models;

import java.util.ArrayList;

public class Query {

    private ArrayList<Coordinate> coordinates;
    private String profile;
    private String format;

    public Query(ArrayList<Coordinate> coordinates, String profile, String format) {
        this.coordinates = coordinates;
        this.profile = profile;
        this.format = format;
    }

    public ArrayList<Coordinate> getCoordinates() {
        return coordinates;
    }

    public String getProfile() {
        return profile;
    }

    public String getFormat() {
        return format;
    }
}
