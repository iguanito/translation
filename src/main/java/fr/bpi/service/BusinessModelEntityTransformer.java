package fr.bpi.service;

import java.util.Locale;
import java.util.Set;
import org.springframework.stereotype.Service;
import fr.bpi.domain.Business;
import fr.bpi.domain.BusinessTranslation;
import fr.bpi.model.BusinessModel;

@Service
public class BusinessModelEntityTransformer {

    public BusinessModel toModel(Business business, Locale locale) {

        BusinessTranslation translation = business.getTranslation(locale);

        return BusinessModel.builder()
                            .id(business.getId())
                            .domain(business.getDomain())
                            .description(translation.getDescription())
                            .valueProposition(translation.getValueProposition())
                            .locale(translation.getLocale())
                            .build();
    }

    public BusinessModel toModelUsingDefaultLanguage(Business business) {
        BusinessTranslation translation = business.getDefault();

        return BusinessModel.builder()
                            .id(business.getId())
                            .domain(business.getDomain())
                            .description(translation.getDescription())
                            .valueProposition(translation.getValueProposition())
                            .locale(translation.getLocale())
                            .build();
    }

    public Business toEntity(BusinessModel businessModel, Set<BusinessTranslation> translations) {
        return Business.builder()
                       .id(businessModel.getId())
                       .domain(businessModel.getDomain())
                       .translations(translations)
                       .build();
    }

    public BusinessTranslation toDefaultTranslation(BusinessModel business) {

        return BusinessTranslation.builder()
                                  .locale(business.getLocale())
                                  .description(business.getDescription())
                                  .valueProposition(business.getValueProposition())
                                  .isDefault(true).build();
    }

}
