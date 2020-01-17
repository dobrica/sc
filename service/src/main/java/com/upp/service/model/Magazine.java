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
public class Magazine {

    private String issn;
    private String title;
    private List<String> scientificFields;
    private List<String> paymentTypes;
    private String mainEditor;
    private List<String> editors;
    private List<String> reviewers;
    // OPEN-ACCESS: Autor of scientific paper pays subscription to magazine
    // NOT OPEN-ACCESS: Reader of scientific paper pays subscription to magazine
    private Boolean isOpenAccess;
    private Float subscriptionFeeAmount;
    private String status; //ACTIVE, INACTIVE

}
