package fr.bpi.model;

import java.util.Locale;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusinessModel {

    private Integer id;
    private String domain;
    private String description;
    private String valueProposition;
    private Locale locale;

}
