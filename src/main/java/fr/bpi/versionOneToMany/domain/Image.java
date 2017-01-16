package fr.bpi.versionOneToMany.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Image {

    @Id
    @GeneratedValue
    private Long id;

    private byte[] image;

    private String title;

    private Long createdAt;




}
