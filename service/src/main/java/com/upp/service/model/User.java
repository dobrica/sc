package com.upp.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

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

}
