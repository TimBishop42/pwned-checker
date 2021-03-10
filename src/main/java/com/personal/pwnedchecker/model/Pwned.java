package com.personal.pwnedchecker.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Data
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
    private boolean isVerified;
    private boolean isFabricated;
    private boolean isSensitive;
    private boolean isRetired;
    private boolean isSpamList;
    private String logoPath;



}
