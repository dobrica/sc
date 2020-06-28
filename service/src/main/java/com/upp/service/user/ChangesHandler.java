package com.upp.service.user;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class ChangesHandler implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String newTitle = (String) delegateExecution.getVariable("new_title");
        String newApstract = (String) delegateExecution.getVariable("new_apstract");
        String newKeywords = (String) delegateExecution.getVariable("new_keywords");
        String newPdf = (String) delegateExecution.getVariable("new_pdf");

        delegateExecution.setVariable("title", newTitle);
        delegateExecution.setVariable("apstract", newApstract);
        delegateExecution.setVariable("keywords", newKeywords);
        delegateExecution.setVariable("pdf", newPdf);
    }

}
