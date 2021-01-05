package com.mickwerf.digi_tours_breda.live_data.route_logic.ors.models;

import java.util.ArrayList;

public class Segment {

    private double distance;
    private int duration;
    private ArrayList<Step> steps;

    public Segment(double distance, int duration, ArrayList<Step> steps) {
        this.distance = distance;
        this.duration = duration;
        this.steps = steps;
    }

    public double getDistance() {
        return distance;
    }

    public int getDuration() {
        return duration;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }
}
