package com.personal.pwnedchecker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pwned implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userEmail;

    private String name;
    private String title;
    private Date breachDate;
    @Column(length = 1000)
    private String description;
    private Long pwnCount;
    private String domain;
    private Date addedDate;

    private Date modifiedDate;
    @Convert(converter =  StringListConverter.class)
    private List<String> dataClasses;
    private String isVerified;
    private String isFabricated;
    private String isSensitive;
    private String isRetired;
    private String isSpamList;
    private String logoPath;



}
