package com.upp.service.scientificpaper;

import com.upp.service.model.ScientificPaper;
import com.upp.service.model.ScientificPaperDBService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SaveScientificPaperHandler implements JavaDelegate {

    @Autowired
    ScientificPaperDBService scientificPaperDBService;

    @Override
    public void execute(DelegateExecution de) throws Exception {
        ScientificPaper sp = scientificPaperDBService.findScientificPaperById((String) de.getVariable("spId"));
        sp.setTitle((String) de.getVariable("title"));
        sp.setAbstrct((String) de.getVariable("apstract"));
        sp.setKeywords((String) de.getVariable("keywords"));
        String pdfName = (String) de.getVariable("pdf");
        pdfName = pdfName.replace("C:\\fakepath\\", "");
        sp.setPdfName(pdfName);
        sp.setFee(100); // default value
        sp.setMagazineId((String) de.getVariable("magazine"));
        sp.setScientificField((String) de.getVariable("scientificField"));
        sp.setDOI(UUID.randomUUID().toString());
        scientificPaperDBService.saveScientificPaper(sp);
    }
}
