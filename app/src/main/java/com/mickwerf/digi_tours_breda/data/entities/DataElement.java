package com.mickwerf.digi_tours_breda.data.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Room entity which describes a data Element.
 */
@Entity(tableName = "data_element")
public class DataElement {
    @NonNull
    @PrimaryKey
    private String path;

    @NonNull
    @ColumnInfo(name = "data_type")
    private String dataType;

    @NonNull
    @ColumnInfo(name = "location")
    private String location;

    @NonNull
    @ColumnInfo(name = "language")
    private String language;

    public DataElement(@NonNull String path, @NonNull String dataType, @NonNull String location, @NonNull String language) {
        this.path = path;
        this.dataType = dataType;
        this.location = location;
        this.language = language;
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
    public String getLocation() {
        return location;
    }

    public void setLocation(@NonNull String location) {
        this.location = location;
    }

    @NonNull
    public String getLanguage() {
        return language;
    }

    public void setLanguage(@NonNull String language) {
        this.language = language;
    }
}
