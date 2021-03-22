package com.personal.pwnedchecker.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public class Pwned implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    private String userEmail;

    @JsonProperty("Name")
    private String name;
    @JsonProperty("Title")
    private String title;
    @JsonProperty("Domain")
    private String domain;
    @JsonProperty("BreachDate")
    @Convert(converter = DateConverter.class)
    private String breachDate;
    @JsonProperty("AddedDate")
    @Convert(converter = DateTimeConverter.class)
    private String addedDate;
    @JsonProperty("ModifiedDate")
    @Convert(converter = DateTimeConverter.class)
    private String modifiedDate;
    @JsonProperty("PwnCount")
    private Integer pwnCount;
    @Column(length = 1000)
    @JsonProperty("Description")
    private String description;
    @JsonProperty("LogoPath")
    private String logoPath;
    @JsonProperty("DataClasses")
    @Convert(converter =  StringListConverter.class)
    private List<String> dataClasses = null;
    @JsonProperty("IsVerified")
    private Boolean isVerified;
    @JsonProperty("IsFabricated")
    private Boolean isFabricated;
    @JsonProperty("IsSensitive")
    private Boolean isSensitive;
    @JsonProperty("IsRetired")
    private Boolean isRetired;
    @JsonProperty("IsSpamList")
    private Boolean isSpamList;

    @Transient
    private String rawDescription;

    public void truncDescription() {
        rawDescription = description;
        if(description.length() > 1000) { //max DB field length
            this.description = description.substring(0, 999);
        }
    }




}
