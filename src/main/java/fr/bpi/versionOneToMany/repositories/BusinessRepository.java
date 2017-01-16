package fr.bpi.versionOneToMany.repositories;

import org.springframework.data.repository.CrudRepository;
import fr.bpi.versionOneToMany.domain.Business;

public interface BusinessRepository extends CrudRepository<Business, Integer> {

}
