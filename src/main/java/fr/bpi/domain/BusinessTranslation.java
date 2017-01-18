package fr.bpi.domain;

import java.util.Locale;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "catalog_business_translation")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class BusinessTranslation {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "languageCode")
    private Locale locale;

    private String description;

    private String valueProposition;

    //TBD
    @Column(name="catalog_business_id")
    private Integer businessId;

    @Column(name = "is_default")
    private boolean isDefault;
}