package fr.bpi.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import java.net.URI;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import fr.bpi.domain.Business;
import fr.bpi.domain.BusinessTranslation;
import fr.bpi.repositories.BusinessRepository;
import fr.bpi.repositories.BusinessTranslationRepository;
import fr.bpi.service.BusinessDefaultingTranslator;

@RestController()
@RequestMapping("/businesses")
public class BusinessController {

    private final BusinessRepository businessRepository;

    private final BusinessTranslationRepository businessTranslationRepository;

    private final BusinessDefaultingTranslator businessTranslator;

    @Autowired
    public BusinessController(BusinessTranslationRepository businessTranslationRepository, BusinessDefaultingTranslator businessTranslator, BusinessRepository businessRepository) {
        this.businessTranslationRepository = businessTranslationRepository;
        this.businessTranslator = businessTranslator;
        this.businessRepository = businessRepository;
    }

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

        if (language == null) {
            return ResponseEntity.ok(business);
        } else {
            return ResponseEntity.ok(businessTranslator.translate(business, new Locale(language)));
        }

    }

    @RequestMapping(method = POST)
    ResponseEntity<?> create(@RequestBody Business business) {
        if (business.getDescription() == null || business.getValueProposition() == null) {
            return ResponseEntity.badRequest().build();
        }

        Business savedBusiness = businessRepository.save(business);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedBusiness.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @RequestMapping(path = "/{id}", method = PUT)
    ResponseEntity<?> update(@PathVariable("id") Integer businessId, @RequestBody Business updatedBusiness) {
        //no update of translation?
        Business savedBusiness = businessRepository.findOne(businessId);
        if (savedBusiness == null) {
            return ResponseEntity.notFound().build();
        }

        if(!businessId.equals(updatedBusiness.getId())){
            return ResponseEntity.badRequest().build();
        }

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

    @RequestMapping(path = "/{id}/translations", method = RequestMethod.POST)
    ResponseEntity<?> create(@PathVariable("id") Integer businessId, @RequestBody BusinessTranslation translation) {
        Business savedBusiness = businessRepository.findOne(businessId);
        if (savedBusiness == null) {
            return ResponseEntity.notFound().build();
        }

        BusinessTranslation businessTranslation = BusinessTranslation.builder()
                                                                     .description(translation.getDescription())
                                                                     .valueProposition(translation.getValueProposition())
                                                                     .businessId(savedBusiness.getId())
                                                                     .locale(translation.getLocale())
                                                                     .build();

        BusinessTranslation savedTranslation = businessTranslationRepository.save(businessTranslation);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedTranslation.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

}
