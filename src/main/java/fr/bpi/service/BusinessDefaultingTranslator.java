package fr.bpi.service;

import java.util.Locale;
import java.util.StringJoiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;
import fr.bpi.domain.Business;

@Service
public class BusinessDefaultingTranslator {

    //Think about a visitor pattern to generalise to all entities

    private final DatabaseMessageSource databaseMessageSource;

    @Autowired
    public BusinessDefaultingTranslator(DatabaseMessageSource databaseMessageSource) {
        this.databaseMessageSource = databaseMessageSource;
    }

    public Business translate(Business business, Locale locale){
        if (locale == null || locale.getLanguage() == null) {
            return business;
        }
        String delimiter = ".";
        String prefix = new StringJoiner(delimiter).add(business.getTranslationTableName())
                                             .add(business.getTranslationTableReferringField())
                                             .add(business.getId().toString()).toString();

        try {
            String translatedDescription = databaseMessageSource.getMessage(prefix + delimiter + business.getDescriptionKey(),
                                                                            null,
                                                                            locale);
            String translatedValue = databaseMessageSource.getMessage(prefix + delimiter + business.getValuePropositionkey()
                                                                            .toString(),
                                                                      null,
                                                                      locale);
            business.setDescription(translatedDescription);
            business.setValueProposition(translatedValue);

            return business;
        } catch (NoSuchMessageException e) {
            //log
            return business;
        }
    }

}
