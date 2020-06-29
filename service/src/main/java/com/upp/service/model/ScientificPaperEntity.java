package com.upp.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
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

    @ElementCollection(targetClass = String.class)
    @Column(name = "coauthors")
    private List<String> coauthors;

    public ScientificPaperEntity(String id, String title, String abstrct, String keywords,
                                 double fee, String pdfName, byte[] pdf, String scientificField, String magazineId) {
        this.id = id;
        this.title = title;
        this.abstrct = abstrct;
        this.keywords = keywords;
        this.fee = fee;
        this.pdfName = pdfName;
        this.pdf = pdf;
        this.scientificField = scientificField;
        this.magazineId = magazineId;
    }
}
