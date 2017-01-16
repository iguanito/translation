package fr.bpi.versionOneModel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import fr.bpi.versionOneModel.domain.Business;
import fr.bpi.versionOneModel.domain.BusinessTranslation;
import fr.bpi.versionOneModel.repositories.BusinessTranslationRepository;


public class BusinessTranslator {

    @Autowired
    BusinessTranslationRepository businessTranslationRepository;


    public Business translate(Business business, String language) {
        BusinessTranslation translation = businessTranslationRepository.findByBusinessAndLanguage(business,
                                                                                                  language);

        business.setDescription(translation.getDescription());
        business.setValueProposition(translation.setValueProposition();
    }
}
