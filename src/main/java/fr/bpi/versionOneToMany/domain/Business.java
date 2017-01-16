package fr.bpi.versionOneToMany.domain;

import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "catalog_business")
public class Business {

    @Id
    @GeneratedValue
    private Integer id;
    private String domain;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="catalog_business_id")
    private Set<BusinessTranslation> translations = new HashSet<>();

    public Business() {
    }

    public Business(BusinessTranslation businessTranslation) {
        this.translations.add(businessTranslation);
    }

    public Optional<BusinessTranslation> getTranslation(Locale locale) {
        return translations.stream().filter(t -> t.getLocale().getLanguage().equals(locale.getLanguage())).findFirst();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Set<BusinessTranslation> getTranslations() {
        return translations;
    }

    public void setTranslations(Set<BusinessTranslation> translations) {
        this.translations = translations;
    }


}
