package fr.bpi.service;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fr.bpi.domain.Business;
import fr.bpi.domain.BusinessTranslation;
import fr.bpi.repositories.BusinessTranslationRepository;

@Service
public class BusinessTranslator {

    @Autowired
    private BusinessTranslationRepository businessTranslationRepository;

    public Business translate(Business business, String language) {
        BusinessTranslation translation = businessTranslationRepository.findByBusinessAndLocale(business,
                                                                                                new Locale(language));

        business.setDescription(translation.getDescription());
        business.setValueProposition(translation.getValueProposition());

        return business;
    }
}
