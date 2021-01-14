package com.mickwerf.digi_tours_breda.data.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * Room entity which describes a gps coordinate.
 */
@Entity(tableName = "gps_coordinate")
public class GpsCoordinate implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "latitude")
    private double latitude;

    @ColumnInfo(name = "longitude")
    private double longitude;

    @ForeignKey(
            entity = Location.class,
            parentColumns = "location",
            childColumns = "locationName"
    )
    @NonNull
    @ColumnInfo(name = "location")
    private String location;

    public GpsCoordinate(double latitude, double longitude, @NonNull String location) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.location = location;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @NonNull
    public String getLocation() {
        return location;
    }

    public void setLocation(@NonNull String location) {
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
