package com.upp.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode
@Table(name = "SCIENTIFIC_PAPER")
@AllArgsConstructor
@NoArgsConstructor
public class ScientificPaperEntity implements Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    private String id;

    @Column(name = "DOI")
    private String doi;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "ABSTRACT")
    private String abstrct;

    @Column(name = "KEYWORDS")
    private String keywords;

    @Column(name = "FEE")
    private double fee;

    @Column(name = "PDF_NAME")
    private String pdfName;

    @Column(name = "PDF")
    @Lob
    private byte[] pdf;

    @Column(name = "SCIENTIFIC_FIELD")
    private String scientificField;

    @Column(name = "MAGAZINE_ID")
    private String magazineId;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "SCIENTIFIC_PAPER_ENTITY_COAUTHORS",
            joinColumns = { @JoinColumn(name = "SCIENTIFIC_PAPER_ENTITY_ID") },
            inverseJoinColumns = { @JoinColumn(name = "USERNAME") }
    )
    List<UserEntity> coauthors = new ArrayList<>();
}
