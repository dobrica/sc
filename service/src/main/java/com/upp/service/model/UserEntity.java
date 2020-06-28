package com.upp.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode
@Table(name = "USER")
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @Column(name = "ID", nullable = false)
    private String id;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "FIRSTNAME")
    private String fistname;
    @Column(name = "LASTNAME")
    private String lastname;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "USERNAME", nullable = false)
    private String username;
    @Column(name = "PASSWORD", nullable = false)
    private String password;
    @Column(name = "CITY")
    private String city;
    @Column(name = "STATE")
    private String state;
    @Column(name = "SCIENTIFIC_FIELDS")
    @ElementCollection(targetClass=String.class)
    private List<String> scientificFields;
    @Column(name = "IS_REVIEWER", nullable = false)
    private Boolean isReviewer;
    @Column(name = "IS_EDITOR", nullable = false)
    private Boolean isEditor;
    @Column(name = "SUBSCRIPTION_STATUS", nullable = false)
    private Boolean subscriptionStatus;

    public UserEntity(String id, String email, String username, String password, Boolean isReviewer, Boolean isEditor, Boolean subscriptionStatus) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.isReviewer = isReviewer;
        this.isEditor = isEditor;
        this.subscriptionStatus = subscriptionStatus;
    }

}
