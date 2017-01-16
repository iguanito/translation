package fr.bpi.controllers;

import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import fr.bpi.domain.BusinessTranslation;
import fr.bpi.repositories.BusinessTranslationRepository;

@RestController()
@RequestMapping("/translations")
public class BusinessTranslationController {

    @Autowired
    BusinessTranslationRepository businessTranslationRepository;

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> create(@RequestBody BusinessTranslation translation) {
        BusinessTranslation savedTranslation = businessTranslationRepository.save(translation);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedTranslation.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

}
