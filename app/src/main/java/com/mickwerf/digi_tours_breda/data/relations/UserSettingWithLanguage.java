package com.mickwerf.digi_tours_breda.data.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.mickwerf.digi_tours_breda.data.entities.Language;
import com.mickwerf.digi_tours_breda.data.entities.UserSettings;

/**
 * Room relation which relates the user settings to a language, which depicts the current language.
 */
public class UserSettingWithLanguage {
    @Embedded
    private UserSettings userSettings;

    @Relation(
            parentColumn = "language",
            entityColumn = "languageName"
    )
    private Language language;

    public void setUserSettings(UserSettings userSettings) {
        this.userSettings = userSettings;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public UserSettings getUserSettings() {
        return userSettings;
    }

    public Language getLanguage() {
        return language;
    }
}
