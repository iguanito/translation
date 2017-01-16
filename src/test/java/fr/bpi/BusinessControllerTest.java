package fr.bpi;

import static org.assertj.core.api.Assertions.assertThat;
import java.net.URI;
import java.util.Locale;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import fr.bpi.domain.Business;
import fr.bpi.domain.BusinessTranslation;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BusinessControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    private String businessUrl = "/businesses";
    private String businessTranslationUrl = "/translations";

    @Test
    public void should_create_business_with_default() throws Exception {
        //Given
        Business business = new Business("description", "proposition de valeur");
        business.setDomain("un domaine");

        //When
        URI uri = restTemplate.postForLocation(businessUrl, business);
        Business createdBusiness = restTemplate.getForObject(uri, Business.class);

        //Then
        assertThat(createdBusiness.getDomain()).isEqualTo("un domaine");
        assertThat(createdBusiness.getDescription()).isEqualTo("description");
        assertThat(createdBusiness.getValueProposition()).isEqualTo("proposition de valeur");
    }

    @Test
    //do we?
    public void should_update_default_value() {
        //Given
        Business business = new Business("description", "proposition de valeur");
        business.setDomain("un domaine");

        URI uri = restTemplate.postForLocation(businessUrl, business);
        Business createdBusiness = restTemplate.getForObject(uri, Business.class);

        createdBusiness.setValueProposition("la nouvelle proposition de valeur");

        //When
        restTemplate.put(uri, createdBusiness);

        //Then
        Business updatedBusiness = restTemplate.getForObject(uri, Business.class);
        assertThat(updatedBusiness.getDomain()).isEqualTo("un domaine");
        assertThat(createdBusiness.getDescription()).isEqualTo("description");
        assertThat(createdBusiness.getValueProposition()).isEqualTo("la nouvelle proposition de valeur");
    }

    @Test
    public void should_get_translated_business() {
        //Given
        Business business = new Business("description", "proposition de valeur");
        business.setDomain("un domaine");

        URI uri = restTemplate.postForLocation(businessUrl, business);
        Business createdBusiness = restTemplate.getForObject(uri, Business.class);

        BusinessTranslation translation = new BusinessTranslation(createdBusiness, Locale.ENGLISH,
                                                                  "the description",
                                                                  "the value proposition");
        restTemplate.postForLocation(businessTranslationUrl, translation);

        //When
        Business translatedBusiness = restTemplate.getForObject(uri.toString() + "?language=en", Business.class);

        //Then
        assertThat(translatedBusiness.getDomain()).isEqualTo("un domaine");
        assertThat(translatedBusiness.getDescription()).isEqualTo("the description");
        assertThat(translatedBusiness.getValueProposition()).isEqualTo("the value proposition");
    }

    @Test
    public void should_not_accept_business_without_translation() {
        //Given
        Business business = new Business();

        //When
        ResponseEntity<Business> businessResponseEntity = restTemplate.postForEntity(businessUrl,
                                                                                     business,
                                                                                     Business.class);
        //Then
        assertThat(businessResponseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    @Ignore
    public void should_not_accept_business_with_several_translations_in_same_language() {
        //Given
        Business business = new Business();

  /*      BusinessTranslation translation = new BusinessTranslation(Locale.FRENCH,
                                                                  "description",
                                                                  "proposition de valeur");
        BusinessTranslation anotherTranslation = new BusinessTranslation(Locale.FRENCH,
                                                                         "description",
                                                                         "proposition de valeur");*/

        //business.getTranslations().add(translation);
        //business.getTranslations().add(anotherTranslation);

        //When
        ResponseEntity<Business> businessResponseEntity = restTemplate.postForEntity(businessUrl,
                                                                                     business,
                                                                                     Business.class);

        //Then
        assertThat(businessResponseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    @Ignore
    public void should_delete_business() {
        //Given
/*        BusinessTranslation translation = new BusinessTranslation(Locale.FRENCH,
                                                                  "description",
                                                                  "proposition de valeur");*/
        //Business business = new Business(translation);
        //business.setDomain("un domaine");


        //When

    }

}
