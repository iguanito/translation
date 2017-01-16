package fr.bpi.versionOneModel.controller;

import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import fr.bpi.versionOneModel.domain.BusinessTranslation;
import fr.bpi.versionOneModel.repositories.BusinessTranslationRepository;

@RestController
@RequestMapping("/version1/businesses_translation")
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
