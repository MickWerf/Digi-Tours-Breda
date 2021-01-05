package com.mickwerf.digi_tours_breda.live_data.route_logic.ors.models;

public class Step {

    private double distance;
    private double duration;
    private String instruction;
    private String name;
    private int[] wayPoints;

    public Step(double distance, double duration, String instruction, String name, int[] wayPoints) {
        this.distance = distance;
        this.duration = duration;
        this.instruction = instruction;
        this.name = name;
        this.wayPoints = wayPoints;
    }

    public double getDistance() {
        return distance;
    }

    public double getDuration() {
        return duration;
    }

    public String getInstruction() {
        return instruction;
    }

    public String getName() {
        return name;
    }

    public int[] getWayPoints() {
        return wayPoints;
    }
}
