package com.upp.service.user;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.ArrayList;
import java.util.List;

public class ReviewersNumberHandler implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        delegateExecution.setVariable("reviewersNumber", 1);
        List<String> reviewers = (List<String>) delegateExecution.getVariable("reviewers");
        if(reviewers.size() > 0) {
            delegateExecution.setVariable("reviewers", new ArrayList<>());
        }
    }

}
