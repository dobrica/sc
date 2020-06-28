package com.upp.service.scientificpaper;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.Arrays;
import java.util.List;

public class ParseKeywordsHandler implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String kwrds = (String) delegateExecution.getVariable("keywords");
        List<String> keywords = Arrays.asList(kwrds.split(","));
    }
}
