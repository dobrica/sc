package com.upp.service.scientificpaper;

import com.upp.service.model.ScientificPaperDBService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RemoveRejectedFileHandler implements JavaDelegate {

    @Autowired
    ScientificPaperDBService scientificPaperDBService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        if((boolean)delegateExecution.getVariable("isRejected")){
            String id = (String) delegateExecution.getVariable("spId");
            scientificPaperDBService.deleteScientificPaper(id);
        }
    }
}
