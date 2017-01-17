package fr.bpi;

import static java.util.Locale.ENGLISH;
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
import fr.bpi.model.BusinessModel;
import fr.bpi.model.BusinessTranslationModel;

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
        BusinessModel businessModel = BusinessModel.builder()
                                                   .domain("un domaine")
                                                   .description("description")
                                                   .valueProposition("proposition de valeur")
                                                   .locale(Locale.FRENCH).build();

        //When
        URI uri = restTemplate.postForLocation(businessUrl, businessModel);
        BusinessModel createdBusiness = restTemplate.getForObject(uri, BusinessModel.class);

        //Then
        assertThat(createdBusiness.getDomain()).isEqualTo("un domaine");
        assertThat(createdBusiness.getDescription()).isEqualTo("description");
        assertThat(createdBusiness.getValueProposition()).isEqualTo("proposition de valeur");
    }

    @Test
    //do we?
    public void should_NOT_update_default_value() {
        //Given
        BusinessModel businessModel = BusinessModel.builder()
                                                   .domain("un domaine")
                                                   .description("description")
                                                   .valueProposition("proposition de valeur")
                                                   .locale(Locale.FRENCH).build();

        URI uri = restTemplate.postForLocation(businessUrl, businessModel);
        BusinessModel createdBusiness = restTemplate.getForObject(uri, BusinessModel.class);

        createdBusiness.setValueProposition("la nouvelle proposition de valeur");
        createdBusiness.setDomain("un autre domaine");

        //When
        restTemplate.put(uri, createdBusiness);

        //Then
        BusinessModel updatedBusiness = restTemplate.getForObject(uri, BusinessModel.class);
        assertThat(updatedBusiness.getDomain()).isEqualTo("un autre domaine");
        assertThat(updatedBusiness.getDescription()).isEqualTo("description");
        assertThat(updatedBusiness.getValueProposition()).isEqualTo("proposition de valeur");
    }

    @Test
    public void should_get_translated_business() {
        //Given
        BusinessModel businessModel = BusinessModel.builder()
                                                   .domain("un domaine")
                                                   .description("la description")
                                                   .valueProposition("la proposition de valeur")
                                                   .locale(Locale.FRENCH).build();

        URI uri = restTemplate.postForLocation(businessUrl, businessModel);
        BusinessModel createdBusiness = restTemplate.getForObject(uri, BusinessModel.class);

        BusinessTranslationModel translation = BusinessTranslationModel.builder()
                                                                       .locale(ENGLISH)
                                                                       .description("a description")
                                                                       .valueProposition("a value proposition")
                                                                       .build();
        restTemplate.postForLocation(businessUrl + "/" + createdBusiness.getId() + businessTranslationUrl, translation);

        //When
        BusinessModel translatedBusiness = restTemplate.getForObject(uri.toString() + "?language=en", BusinessModel.class);

        //Then
        assertThat(translatedBusiness.getDomain()).isEqualTo("un domaine");
        assertThat(translatedBusiness.getDescription()).isEqualTo("a description");
        assertThat(translatedBusiness.getValueProposition()).isEqualTo("a value proposition");
    }

    @Test
    public void should_not_accept_business_without_translation() {
        //Given
        BusinessModel business = new BusinessModel();

        //When
        ResponseEntity<BusinessModel> businessResponseEntity = restTemplate.postForEntity(businessUrl,
                                                                                     business,
                                                                                     BusinessModel.class);
        //Then
        assertThat(businessResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }


    @Test
    public void should_delete_business() {
        BusinessModel businessModel = BusinessModel.builder()
                                                   .domain("un domaine")
                                                   .description("la description")
                                                   .valueProposition("la proposition de valeur")
                                                   .locale(Locale.FRENCH).build();

        URI uri = restTemplate.postForLocation(businessUrl, businessModel);

        restTemplate.delete(uri);

        ResponseEntity<BusinessModel> entity = restTemplate.getForEntity(uri, BusinessModel.class);

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }

}
