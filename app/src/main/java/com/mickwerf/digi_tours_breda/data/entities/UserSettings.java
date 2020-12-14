package com.mickwerf.digi_tours_breda.data.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_settings")
public class UserSettings {
    @NonNull
    @PrimaryKey
    private String language;

    public UserSettings(@NonNull String language) {
        this.language = language;
    }

    @NonNull
    public String getLanguage() {
        return language;
    }

    public void setLanguage(@NonNull String language) {
        this.language = language;
    }
}
