package fr.bpi.domain;

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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "catalog_business")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Business {

    @Id
    @GeneratedValue
    private Integer id;
    private String domain;

    //would we like to use a Map instead of a Set here?
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="catalog_business_id")
    private Set<BusinessTranslation> translations = new HashSet<>();

    public Business(BusinessTranslation businessTranslation) {
        this.translations.add(businessTranslation);
    }

    public BusinessTranslation getTranslation(Locale locale) {
        return translations.stream().filter(t -> t.getLocale().getLanguage().equals(locale.getLanguage())).findFirst().orElse(this.getDefault());
    }

    public BusinessTranslation getDefault() {
        Optional<BusinessTranslation> defaultTranslation = translations.stream().filter(BusinessTranslation::isDefault).findFirst();

        return defaultTranslation.orElseThrow(() -> new IllegalStateException("no default language found"));
    }

}
