package com.mickwerf.digi_tours_breda.data.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * Room entity which describes the user settings.
 */
@Entity(tableName = "user_settings")
public class UserSettings {
    @PrimaryKey
    private int settingsId;

    @ForeignKey(
            entity = Language.class,
            parentColumns = "language",
            childColumns = "languageName"
    )
    @NonNull
    @ColumnInfo(name = "language")
    private String language;

    @ForeignKey(
            entity = Route.class,
            parentColumns = "route",
            childColumns = "routeName"
    )
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
