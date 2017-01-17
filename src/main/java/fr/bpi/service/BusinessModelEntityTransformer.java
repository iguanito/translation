package fr.bpi.service;

import org.springframework.stereotype.Service;
import fr.bpi.domain.Business;
import fr.bpi.domain.BusinessTranslation;
import fr.bpi.model.BusinessModel;

@Service
public class BusinessModelEntityTransformer {

    public BusinessModel toModel(Business business, BusinessTranslation translation) {
        return BusinessModel.builder()
                            .id(business.getId())
                            .domain(business.getDomain())
                            .description(translation.getDescription())
                            .valueProposition(translation.getValueProposition())
                            .locale(translation.getLocale())
                            .build();
    }

    public Business toEntity(BusinessModel businessModel) {
        return Business.builder()
                       .id(businessModel.getId())
                       .domain(businessModel.getDomain()).build();
    }
}
