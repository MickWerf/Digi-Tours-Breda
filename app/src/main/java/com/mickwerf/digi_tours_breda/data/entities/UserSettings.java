package com.mickwerf.digi_tours_breda.data.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Room entity which describes the user settings.
 */
@Entity(tableName = "user_settings")
public class UserSettings {

    @PrimaryKey
    private int settingsId;

    @NonNull
    @ColumnInfo(name = "language")
    private String language;

    @NonNull
    @ColumnInfo(name = "route")
    private String route;

    public UserSettings(int settingsId, @NonNull String language, @NonNull String route) {
        this.settingsId = settingsId;
        this.language = language;
        this.route = route;
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

    @NonNull
    public String getRoute() {
        return route;
    }

    public void setRoute(@NonNull String route) {
        this.route = route;
    }
}
