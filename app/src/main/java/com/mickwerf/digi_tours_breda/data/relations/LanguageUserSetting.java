package com.mickwerf.digi_tours_breda.data.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.mickwerf.digi_tours_breda.data.entities.Language;
import com.mickwerf.digi_tours_breda.data.entities.UserSettings;

public class LanguageUserSetting {
    @Embedded
    private UserSettings userSettings;

    @Relation(
            parentColumn = "language",
            entityColumn = "languageName"
    )
    private Language language;
}
