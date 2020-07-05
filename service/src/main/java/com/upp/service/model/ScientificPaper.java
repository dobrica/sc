package com.upp.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScientificPaper implements Serializable {
    private String id;
    private String DOI;
    private String title;
    private String abstrct;
    private String keywords;
    private double fee;
    private String pdfName;
    private byte[] pdf;
    private String scientificField;
    private String magazineId;
    private List<User> coauthors = new ArrayList<>();
}
