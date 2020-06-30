package com.upp.service.user;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class CommentsSumHandler implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String reviewerEditorComment = (String) delegateExecution.getVariable("reviewerEditorComment");
        if (reviewerEditorComment == null) {
            delegateExecution.setVariable("reviewerEditorComment", "");
        }
        reviewerEditorComment += "\n " + (String) delegateExecution.getVariable("commentForEditors");
        delegateExecution.setVariable("reviewerEditorComment", reviewerEditorComment);

        String reviewerAuthorComment = (String) delegateExecution.getVariable("reviewerAuthorComment");
        if (reviewerAuthorComment == null) {
            delegateExecution.setVariable("reviewerAuthorComment", "");
        }
        reviewerAuthorComment += "\n " + (String) delegateExecution.getVariable("commentForAuthor");
        delegateExecution.setVariable("reviewerAuthorComment", reviewerAuthorComment);

        String recommendations = (String) delegateExecution.getVariable("recommendations");
        if (recommendations == null) {
            delegateExecution.setVariable("recommendations", "");
        }
        recommendations += "\n " + delegateExecution.getVariable("recommendation");
        delegateExecution.setVariable("recommendations", recommendations);

    }
}
