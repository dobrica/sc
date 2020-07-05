package com.upp.service.scientificpaper;

import com.upp.service.model.ScientificPaper;
import com.upp.service.model.ScientificPaperDBService;
import com.upp.service.model.User;
import com.upp.service.model.UserDBService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SaveScientificPaperHandler implements JavaDelegate {

    @Autowired
    UserDBService userDBService;

    @Autowired
    ScientificPaperDBService scientificPaperDBService;

    @Override
    public void execute(DelegateExecution de) throws Exception {

        List<User> coauthors = (List<User>) de.getVariable("coauthors");
        for (User ca: coauthors) {
            ca.setId(UUID.randomUUID().toString());
            userDBService.saveUser(ca);
        }

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
        sp.setCoauthors(coauthors);
        sp.setDOI(UUID.randomUUID().toString());
        scientificPaperDBService.saveScientificPaper(sp);
    }
}
