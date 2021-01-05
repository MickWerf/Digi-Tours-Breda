package com.mickwerf.digi_tours_breda.live_data.route_logic.ors.models;

public class Metadata {

    private String attribution;
    private String service;
    private long timestamp;
    private Query query;

    public Metadata(String attribution, String service, long timestamp, Query query) {
        this.attribution = attribution;
        this.service = service;
        this.timestamp = timestamp;
        this.query = query;
    }

    public String getAttribution() {
        return attribution;
    }

    public String getService() {
        return service;
    }

    public double getTimestamp() {
        return timestamp;
    }

    public Query getQuery() {
        return query;
    }
}
