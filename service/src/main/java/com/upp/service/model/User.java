package com.upp.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable, UserDetails {

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
    private Boolean subscriptionStatus;

    public User(String id, String fistname, String lastname, String email, String username, String password, String city, String state, Boolean isReviewer, Boolean isEditor, Boolean subscriptionStatus) {
        this.id = id;
        this.fistname = fistname;
        this.lastname = lastname;
        this.email = email;
        this.username = username;
        this.password = password;
        this.city = city;
        this.state = state;
        this.isReviewer = isReviewer;
        this.isEditor = isEditor;
        this.subscriptionStatus = subscriptionStatus;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
