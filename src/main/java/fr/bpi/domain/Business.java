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
public class Business {

    @Id
    @GeneratedValue
    private Integer id;
    private String domain;

}
