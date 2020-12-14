package com.mickwerf.digi_tours_breda.data.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "data_element")
public class DataElement {
    @NonNull
    @PrimaryKey
    private String path;

    @NonNull
    @ColumnInfo(name = "data_type")
    private String dataType;

    @NonNull
    @ColumnInfo(name = "location_name")
    private String locationName;

    @NonNull
    @ColumnInfo(name = "language_name")
    private String languageName;

    public DataElement(@NonNull String path, @NonNull String dataType, @NonNull String locationName, @NonNull String languageName) {
        this.path = path;
        this.dataType = dataType;
        this.locationName = locationName;
        this.languageName = languageName;
    }

    @NonNull
    public String getPath() {
        return path;
    }

    public void setPath(@NonNull String path) {
        this.path = path;
    }

    @NonNull
    public String getDataType() {
        return dataType;
    }

    public void setDataType(@NonNull String dataType) {
        this.dataType = dataType;
    }

    @NonNull
    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(@NonNull String locationName) {
        this.locationName = locationName;
    }

    @NonNull
    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(@NonNull String languageName) {
        this.languageName = languageName;
    }
}
