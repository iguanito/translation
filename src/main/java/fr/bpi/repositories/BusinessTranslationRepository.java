package fr.bpi.repositories;

import java.util.List;
import java.util.Locale;
import org.springframework.data.repository.CrudRepository;
import fr.bpi.domain.BusinessTranslation;

public interface BusinessTranslationRepository extends CrudRepository<BusinessTranslation, Integer>{

    BusinessTranslation findByBusinessIdAndLocale(Integer businessId, Locale locale);

    List<BusinessTranslation> findByBusinessId(Integer businessId);

    //TODO improve?
    BusinessTranslation findByBusinessIdAndIsDefault(Integer businessId, boolean isDefault);

}
