package com.mickwerf.digi_tours_breda.data.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.mickwerf.digi_tours_breda.data.entities.DataElement;
import com.mickwerf.digi_tours_breda.data.entities.Language;

import java.util.List;

/**
 * Room relation which relates a language to a list of data elements.
 */
public class LanguageWithDataElement {
    @Embedded
    private Language language;

    @Relation(
            parentColumn = "languageName",
            entityColumn = "language"
    )
    private List<DataElement> dataElements;

    public void setLanguage(Language language) {
        this.language = language;
    }

    public void setDataElements(List<DataElement> dataElements) {
        this.dataElements = dataElements;
    }

    public Language getLanguage() {
        return language;
    }

    public List<DataElement> getDataElements() {
        return dataElements;
    }
}
