package com.personal.pwnedchecker.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class Pwned {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userEmail;

    private String name;
    private String title;
    private Date breachDate;
    private String description;
    private Long pwnCount;
    private String domain;
    private Date addedDate;



}
