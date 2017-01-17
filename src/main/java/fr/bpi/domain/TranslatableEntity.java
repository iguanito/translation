package fr.bpi.domain;

public interface TranslatableEntity {

    String TRANSLATION_TABLE_EXTENSION = "_translation";

    String REFERING_ID_EXTENSION = "_id";

    String getTranslationTableName();

    String getTranslationTableReferringField();
}
