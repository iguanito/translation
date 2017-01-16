package fr.bpi.versionOneModel.repositories;

import org.springframework.data.repository.CrudRepository;
import fr.bpi.versionOneModel.domain.Business;
import fr.bpi.versionOneModel.domain.BusinessTranslation;

public interface BusinessTranslationRepository extends CrudRepository<BusinessTranslation, Integer>{

    BusinessTranslation findByBusinessAndLanguage(Business business, String language);

}
