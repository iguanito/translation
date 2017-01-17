package fr.bpi.domain;

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
@Table(name = "catalog_business")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Business implements TranslatableEntity {

    @Id
    @GeneratedValue
    private Integer id;
    private String domain;
    private String valueProposition;
    private String description;

    //should be same as column name
    private final transient String valuePropositionkey = "value_proposition";
    private final transient String descriptionKey = "description";

}
