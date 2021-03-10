package com.personal.pwnedchecker.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;
import java.util.List;


//Todo: not needed
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public class Response {

    String name;
    String title;
    String domain;
    Date breachDate;
    Date addedDate;
    Date modifiedDate;
    Long pwnCount;
    String description;
    String logoPath;
    List<String> dataClasses;
    Boolean isVerified;
    Boolean isFabricate;
    Boolean isSensitive;
    Boolean isRetired;
    Boolean isSpamList;
}
