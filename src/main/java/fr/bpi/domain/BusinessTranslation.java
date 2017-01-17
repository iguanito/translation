package fr.bpi.domain;

import java.util.Locale;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
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

    public BusinessTranslation() {}

    public BusinessTranslation(Locale locale, String description, String valueProposition) {
        this.locale = locale;
        this.description = description;
        this.valueProposition = valueProposition;
    }

}