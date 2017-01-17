package fr.bpi.domain;

import javax.persistence.Table;

public interface TranslatableEntity {

    String TRANSLATION_TABLE_EXTENSION = "_translation";

    String REFERING_ID_EXTENSION = "_id";

    default String getTranslationTableName() {
        return this.getClass().getAnnotation(Table.class).name() + TRANSLATION_TABLE_EXTENSION;
    }

    default String getTranslationTableReferringField() {
        return this.getClass().getAnnotation(Table.class).name() + REFERING_ID_EXTENSION;
    }
}
