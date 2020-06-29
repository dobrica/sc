package com.upp.service.user;

import com.upp.service.model.User;
import com.upp.service.model.UserDBService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChooseEditorHandler implements JavaDelegate {

    @Autowired
    private final UserDBService userDBService;

    public ChooseEditorHandler(final UserDBService userDBService) {
        this.userDBService = userDBService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        User editor = userDBService.findUserById("u124"); //TODO: filter by scientific fields
        delegateExecution.setVariable("editorEmail", "dobrica21@gmail.com");
        delegateExecution.setVariable("editor", "perica");
//        delegateExecution.setVariable("editorEmail", editor.getEmail());
    }

}
