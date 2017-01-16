package fr.bpi.versionOneToMany.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import java.net.URI;
import java.util.Locale;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import fr.bpi.versionOneToMany.domain.Business;
import fr.bpi.versionOneToMany.domain.BusinessTranslation;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= WebEnvironment.RANDOM_PORT)
public class BusinessControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void should_create_business() throws Exception {
        //Given
        BusinessTranslation translation = new BusinessTranslation(Locale.FRENCH,
                                                                  "description",
                                                                  "proposition de valeur");
        Business business = new Business(translation);
        business.setDomain("un domaine");

        //When
        URI uri = restTemplate.postForLocation("/version2/businesses", business);
        Business createdBusiness = restTemplate.getForObject(uri, Business.class);

        //Then
        assertThat(createdBusiness.getDomain()).isEqualTo("un domaine");
        assertThat(createdBusiness.getTranslation(Locale.FRANCE).get().getDescription()).isEqualTo("description");
        assertThat(createdBusiness.getTranslation(Locale.FRANCE).get().getValueProposition()).isEqualTo(
                "proposition de valeur");
    }

    @Test
    public void should_update_existing_business_translation() {
        //Given
        BusinessTranslation translation = new BusinessTranslation(Locale.FRENCH,
                                                                  "description",
                                                                  "proposition de valeur");
        Business business = new Business(translation);
        business.setDomain("un domaine");

        URI uri = restTemplate.postForLocation("/version2/businesses", business);
        Business createdBusiness = restTemplate.getForObject(uri.toString(), Business.class);

        createdBusiness.getTranslation(Locale.FRENCH).get().setDescription("la nouvelle description");
        createdBusiness.getTranslation(Locale.FRENCH).get().setValueProposition("la nouvelle proposition de valeur");

        //When
        restTemplate.put(uri, createdBusiness);

        //Then
        Business updatedBusiness = restTemplate.getForObject(uri, Business.class);
        assertThat(updatedBusiness.getDomain()).isEqualTo("un domaine");
        assertThat(updatedBusiness.getTranslation(Locale.FRANCE).get().getDescription()).isEqualTo(
                "la nouvelle description");
        assertThat(updatedBusiness.getTranslation(Locale.FRANCE).get().getValueProposition()).isEqualTo(
                "la nouvelle proposition de valeur");
    }

    @Test
    public void should_add_translation() {
        //Given
        BusinessTranslation translation = new BusinessTranslation(Locale.FRENCH,
                                                                  "description",
                                                                  "proposition de valeur");
        Business business = new Business(translation);
        business.setDomain("un domaine");

        URI uri = restTemplate.postForLocation("/version2/businesses", business);
        Business createdBusiness = restTemplate.getForObject(uri.toString(), Business.class);

        BusinessTranslation newTranslation = new BusinessTranslation(Locale.ENGLISH,
                                                                     "A description",
                                                                     "value proposition");

        //When
        createdBusiness.getTranslations().add(newTranslation);
        restTemplate.put(uri, createdBusiness);

        //Then
        Business updatedBusiness = restTemplate.getForObject(uri, Business.class);
        assertThat(updatedBusiness.getDomain()).isEqualTo("un domaine");
        assertThat(updatedBusiness.getTranslation(Locale.FRANCE).get().getDescription()).isEqualTo("description");
        assertThat(updatedBusiness.getTranslation(Locale.FRANCE).get().getValueProposition()).isEqualTo(
                "proposition de valeur");
        assertThat(updatedBusiness.getTranslation(Locale.ENGLISH).get().getDescription()).isEqualTo("A description");
        assertThat(updatedBusiness.getTranslation(Locale.ENGLISH).get().getValueProposition()).isEqualTo(
                "value proposition");
    }

    @Test
    public void should_not_accept_business_without_translation() {
        //Given
        Business business = new Business();

        //When
        ResponseEntity<Business> businessResponseEntity = restTemplate.postForEntity("/version2/businesses",
                                                                                     business,
                                                                                     Business.class);

        //Then
        assertThat(businessResponseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void should_not_accept_business_with_several_translations_in_same_language() {
        //Given
        Business business = new Business();

        BusinessTranslation translation = new BusinessTranslation(Locale.FRENCH,
                                                                  "description",
                                                                  "proposition de valeur");
        BusinessTranslation anotherTranslation = new BusinessTranslation(Locale.FRENCH,
                                                                  "description",
                                                                  "proposition de valeur");

        business.getTranslations().add(translation);
        business.getTranslations().add(anotherTranslation);

        //When
        ResponseEntity<Business> businessResponseEntity = restTemplate.postForEntity("/version2/businesses",
                                                                                     business,
                                                                                     Business.class);

        //Then
        assertThat(businessResponseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void should_delete_business() {
        //Given
        BusinessTranslation translation = new BusinessTranslation(Locale.FRENCH,
                                                                  "description",
                                                                  "proposition de valeur");
        Business business = new Business(translation);
        business.setDomain("un domaine");

        URI uri = restTemplate.postForLocation("/version2/businesses",
                                               business,
                                               Business.class);

        //When
        restTemplate.delete(uri);

        //Then
        ResponseEntity<Business> entity = restTemplate.getForEntity(uri, Business.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}
