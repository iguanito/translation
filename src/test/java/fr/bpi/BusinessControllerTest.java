package fr.bpi;

import static java.util.Locale.ENGLISH;
import static org.assertj.core.api.Assertions.assertThat;
import java.net.URI;
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
        Business business = Business.builder()
                                    .domain("un domaine")
                                    .description("description")
                                    .valueProposition("proposition de valeur")
                                    .build();

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
    public void should_NOT_update_default_value() {
        //Given
        Business business = Business.builder()
                                    .domain("un domaine")
                                    .description("description")
                                    .valueProposition("proposition de valeur")
                                    .build();

        URI uri = restTemplate.postForLocation(businessUrl, business);
        Business createdBusiness = restTemplate.getForObject(uri, Business.class);

        createdBusiness.setValueProposition("la nouvelle proposition de valeur");

        //When
        restTemplate.put(uri, createdBusiness);

        //Then
        Business updatedBusiness = restTemplate.getForObject(uri, Business.class);
        assertThat(updatedBusiness.getDomain()).isEqualTo("un domaine");
        assertThat(updatedBusiness.getDescription()).isEqualTo("description");
        assertThat(updatedBusiness.getValueProposition()).isEqualTo("proposition de valeur");
    }

    @Test
    public void should_get_translated_business() {
        //Given
        Business business = Business.builder()
                                    .domain("un domaine")
                                    .description("la description")
                                    .valueProposition("la proposition de valeur")
                                    .build();

        URI uri = restTemplate.postForLocation(businessUrl, business);
        Business createdBusiness = restTemplate.getForObject(uri, Business.class);

        BusinessTranslation translation = BusinessTranslation.builder()
                                                             .locale(ENGLISH)
                                                             .description("a description")
                                                             .valueProposition("a value proposition")
                                                             .businessId(business.getId())
                                                             .build();
        restTemplate.postForLocation(businessUrl + "/" + createdBusiness.getId() + businessTranslationUrl, translation);

        //When
        Business translatedBusiness = restTemplate.getForObject(uri.toString() + "?language=en", Business.class);

        //Then
        assertThat(translatedBusiness.getDomain()).isEqualTo("un domaine");
        assertThat(translatedBusiness.getDescription()).isEqualTo("a description");
        assertThat(translatedBusiness.getValueProposition()).isEqualTo("a value proposition");
    }

    @Test
    public void should_get_default_when_language_not_available() {
        //Given
        Business business = Business.builder()
                                    .domain("un domaine")
                                    .description("la description")
                                    .valueProposition("la proposition de valeur")
                                    .build();

        URI uri = restTemplate.postForLocation(businessUrl, business);

        //When
        Business translatedBusiness = restTemplate.getForObject(uri.toString() + "?language=ch", Business.class);

        //Then
        assertThat(translatedBusiness.getDomain()).isEqualTo("un domaine");
        assertThat(translatedBusiness.getDescription()).isEqualTo("la description");
        assertThat(translatedBusiness.getValueProposition()).isEqualTo("la proposition de valeur");
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
        assertThat(businessResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }


    @Test
    public void should_delete_business() {
        Business business = Business.builder()
                                    .domain("un domaine")
                                    .description("la description")
                                    .valueProposition("la proposition de valeur")
                                    .build();

        URI uri = restTemplate.postForLocation(businessUrl, business);

        restTemplate.delete(uri);

        ResponseEntity<Business> entity = restTemplate.getForEntity(uri, Business.class);

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }

}
