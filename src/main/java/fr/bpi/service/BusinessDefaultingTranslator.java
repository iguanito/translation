package fr.bpi.service;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fr.bpi.domain.Business;
import fr.bpi.domain.BusinessTranslation;
import fr.bpi.model.BusinessModel;
import fr.bpi.repositories.BusinessTranslationRepository;

@Service
public class BusinessDefaultingTranslator {

    @Autowired
    private BusinessTranslationRepository businessTranslationRepository;

    @Autowired
    BusinessModelEntityTransformer transformer;

    public BusinessModel translate(Business business, String language) {
        BusinessTranslation translation = null;
        if (language != null) {
            translation = businessTranslationRepository.findByBusinessIdAndLocale(business.getId(), new Locale(language));
        }
        if (translation == null) {
            translation = businessTranslationRepository.findByBusinessIdAndIsDefault(business.getId(), true);
        }
        if (translation == null) {
            throw new IllegalStateException("No default translation found");
        }

        return transformer.toModel(business, translation);
    }
}
