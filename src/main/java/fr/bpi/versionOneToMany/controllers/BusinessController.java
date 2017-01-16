package fr.bpi.versionOneToMany.controllers;

import static java.util.Objects.nonNull;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import fr.bpi.versionOneToMany.domain.Business;
import fr.bpi.versionOneToMany.domain.BusinessTranslation;
import fr.bpi.versionOneToMany.repositories.BusinessRepository;

@RestController
@RequestMapping("version2/businesses")
public class BusinessController {

    //TODO
    //Proposer une API avec un filtre sur la langue p alléger la réponse

    //Faire une API pr la traduction (sub-resource de business)?

    //ou

    //découpler entity et model

    //tester on cascade

    //ajouter flag ready

    @Autowired
    private BusinessRepository businessRepository;

    @RequestMapping(method = GET)
    Iterable<Business> getBusinesses(){
        return businessRepository.findAll();
    }

    @RequestMapping(path="/{id}", method = GET)
    ResponseEntity<?> getBusiness(@PathVariable("id") Integer businessId){
        Business business = businessRepository.findOne(businessId);

        if (business == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(business);
    }

    @RequestMapping(method = POST)
    ResponseEntity<?> create(@RequestBody Business business) {
        this.validate(business);

        Business savedBusiness = businessRepository.save(business);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedBusiness.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @RequestMapping(path = "/{id}", method = PUT)
    ResponseEntity<?> update(@PathVariable("id") Integer businessId, @RequestBody Business updatedBusiness) {
        //checkvalidity?
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


    private void validate(Business business) {
        Set<String> languages = new HashSet<>();
        Set<BusinessTranslation> translations = business.getTranslations();
        if (!translations.isEmpty()) {
            translations.forEach(t -> {
                nonNull(t.getLocale().getLanguage());
                boolean isLanguageAdded = languages.add(t.getLocale().getLanguage());
                if (!isLanguageAdded) {
                    //language in dobule
                    throw new RuntimeException("Business should not have more than 1 translation in a certain language");
                }
            });
        } else {
            //TODO transform
            throw new RuntimeException("Business should have at least one translation");
        }
    }


}
