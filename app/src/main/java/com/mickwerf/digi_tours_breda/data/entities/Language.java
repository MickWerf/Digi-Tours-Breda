package com.mickwerf.digi_tours_breda.data.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Room entity which describes a language.
 */
@Entity(tableName = "language")
public class Language {
    @NonNull
    @PrimaryKey
    private String languageName;

    public Language(@NonNull String languageName) {
        this.languageName = languageName;
    }

    @NonNull
    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(@NonNull String name) {
        this.languageName = name;
    }
}
