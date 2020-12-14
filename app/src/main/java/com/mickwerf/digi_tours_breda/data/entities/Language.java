package com.mickwerf.digi_tours_breda.data.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "language")
public class Language {
    @NonNull
    @PrimaryKey
    private String languageName;

    public Language(@NonNull String languageName) {
        this.languageName = languageName;
    }

    @NonNull
    public String getName() {
        return languageName;
    }

    public void setName(@NonNull String name) {
        this.languageName = name;
    }
}
