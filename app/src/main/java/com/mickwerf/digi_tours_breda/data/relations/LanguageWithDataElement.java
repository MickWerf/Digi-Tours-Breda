package com.mickwerf.digi_tours_breda.data.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.mickwerf.digi_tours_breda.data.entities.DataElement;
import com.mickwerf.digi_tours_breda.data.entities.Language;

import java.util.List;

public class LanguageWithDataElement {
    @Embedded
    private Language language;

    @Relation(
            parentColumn = "languageName",
            entityColumn = "language"
    )
    private List<DataElement> dataElements;
}
