package fr.bpi.versionOneToMany.domain;

import java.util.Locale;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "catalog_business_translation")
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

    public Integer getId() {
        return id;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValueProposition() {
        return valueProposition;
    }

    public void setValueProposition(String valueProposition) {
        this.valueProposition = valueProposition;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
