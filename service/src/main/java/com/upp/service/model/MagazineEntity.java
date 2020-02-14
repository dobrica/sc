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
@Table(name = "MAGAZINE")
@AllArgsConstructor
@NoArgsConstructor
public class MagazineEntity {

    @Id
    @Column(name = "ISSN", nullable = false)
    private String issn;
    @Column(name = "TITLE", nullable = false)
    private String title;
    @ElementCollection(targetClass=String.class)
    @Column(name = "SCIENTIFIC_FIELDS")
    private List<String> scientificFields;
    @ElementCollection(targetClass=String.class)
    @Column(name = "PAYMENT_TYPES")
    private List<String> paymentTypes;
    @Column(name = "MAIN_EDITOR")
    private String mainEditor;
    @ElementCollection(targetClass=String.class)
    @Column(name = "EDITORS")
    private List<String> editors;
    @ElementCollection(targetClass=String.class)
    @Column(name = "REVIEWERS")
    private List<String> reviewers;
    // OPEN-ACCESS: Autor of scientific paper pays subscription to magazine
    // NOT OPEN-ACCESS: Reader of scientific paper pays subscription to magazine
    @Column(name = "IS_OPEN_ACCESS")
    private Boolean isOpenAccess;
    @Column(name = "SUBSCRIPTION_FEE_AMOUNT")
    private Float subscriptionFeeAmount;
    @Column(name = "STATUS")
    private String status; //ACTIVE, INACTIVE

    public MagazineEntity(String issn, String title, Boolean isOpenAccess, String mainEditor) {
        this.setIssn(issn);
        this.setTitle(title);
        this.setIsOpenAccess(isOpenAccess);
        this.setMainEditor(mainEditor);
    }

}
