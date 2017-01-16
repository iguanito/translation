package fr.bpi.versionOneModel.repositories;

import org.springframework.data.repository.CrudRepository;
import fr.bpi.versionOneModel.domain.Business;

public interface BusinessRepository extends CrudRepository<Business, Integer> {

}
