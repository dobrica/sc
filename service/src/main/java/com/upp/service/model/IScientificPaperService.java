package com.upp.service.model;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IScientificPaperService {

    List<ScientificPaper> findAllScientificPapers();
    void saveScientificPaper(ScientificPaper scientificPaper);
    void savePdf(String id, MultipartFile pdf);
    ScientificPaper findScientificPaperById(String id);

}
