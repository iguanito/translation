package fr.bpi.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import fr.bpi.domain.Business;
import fr.bpi.repositories.BusinessRepository;
import fr.bpi.service.BusinessTranslator;

@RestController()
@RequestMapping("/businesses")
public class BusinessController {

    //TODO
    //bi relationial relationship for delete? or on cascade delete? Hibernate annotation onDelete?

    @Autowired
    private BusinessRepository businessRepository;

    @Autowired
    private BusinessTranslator businessTranslator;

    @RequestMapping(method = GET)
    Iterable<Business> getBusinesses(){
        return businessRepository.findAll();
    }


    @RequestMapping(path="/{id}", method = GET)
    ResponseEntity<?> getBusiness(@PathVariable("id") Integer businessId, @RequestParam(name = "language", required = false) String language){
        Business business = businessRepository.findOne(businessId);

        if (business == null) {
            return ResponseEntity.notFound().build();
        }

        if (language != null) {
            business = businessTranslator.translate(business, language);
        }

        return ResponseEntity.ok(business);
    }

    @RequestMapping(method = POST)
    ResponseEntity<?> create(@RequestBody Business business) {
        Business savedBusiness = businessRepository.save(business);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedBusiness.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @RequestMapping(path = "/{id}", method = PUT)
    ResponseEntity<?> update(@PathVariable("id") Integer businessId, @RequestBody Business updatedBusiness) {
        Business savedBusiness = businessRepository.findOne(businessId);
        if (savedBusiness == null) {
            return ResponseEntity.notFound().build();
        }

        if(!businessId.equals(updatedBusiness.getId())){
            return ResponseEntity.badRequest().build();
        }

        businessRepository.save(updatedBusiness);

        return ResponseEntity.ok().build();
    }

    @RequestMapping(path = "/{id}", method = DELETE)
    ResponseEntity<?> delete(@PathVariable("id") Integer businessId) {
        Business savedBusiness = businessRepository.findOne(businessId);
        if (savedBusiness == null) {
            return ResponseEntity.notFound().build();
        }

        businessRepository.delete(savedBusiness);

        return ResponseEntity.noContent().build();
    }

}
