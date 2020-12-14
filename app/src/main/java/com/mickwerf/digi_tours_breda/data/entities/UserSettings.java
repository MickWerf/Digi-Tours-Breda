package com.mickwerf.digi_tours_breda.data.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_settings")
public class UserSettings {

    @PrimaryKey
    private int settingsId;

    @NonNull
    @ColumnInfo(name = "language")
    private String language;

    public UserSettings(int settingsId, @NonNull String language) {
        this.settingsId = settingsId;
        this.language = language;
    }

    @NonNull
    public String getLanguage() {
        return language;
    }

    public void setLanguage(@NonNull String language) {
        this.language = language;
    }

    public int getSettingsId() {
        return settingsId;
    }

    public void setSettingsId(int settingsId) {
        this.settingsId = settingsId;
    }
}
