package com.upp.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private String id;
    private String title;
    private String fistname;
    private String lastname;
    private String email;
    private String username;
    private String password;
    private String city;
    private String state;
    private List<String> scientificFields;
    private Boolean isReviewer;
    private Boolean isEditor;

    public User(String id, String email, String username, String password, Boolean isReviewer, Boolean isEditor) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.isReviewer = isReviewer;
        this.isEditor = isEditor;
    }

}
