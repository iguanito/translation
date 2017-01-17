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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusinessTranslation {

    @Id
    @GeneratedValue
    private Integer id;
    private String description;
    private String valueProposition;
    @Column(name = "language_code")
    private Locale locale;
    @Column(name = "catalog_business_id")
    private Integer businessId;


}
