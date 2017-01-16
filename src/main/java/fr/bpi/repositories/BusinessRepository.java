package fr.bpi.repositories;

import org.springframework.data.repository.CrudRepository;
import fr.bpi.domain.Business;

public interface BusinessRepository extends CrudRepository<Business, Integer> {

}
