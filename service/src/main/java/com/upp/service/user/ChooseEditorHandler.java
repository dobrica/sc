package com.upp.service.user;

import com.upp.service.model.MagazineDBService;
import com.upp.service.model.User;
import com.upp.service.model.UserDBService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChooseEditorHandler implements JavaDelegate {

    @Autowired
    private final MagazineDBService magazineDBService;

    @Autowired
    private final UserDBService userDBService;

    public ChooseEditorHandler(MagazineDBService magazineDBService, final UserDBService userDBService) {
        this.magazineDBService = magazineDBService;
        this.userDBService = userDBService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        var mId = delegateExecution.getVariable("magazine");
        var magazine = magazineDBService.findMagazineById(mId.toString());
        User mainEditor = userDBService.findUserByUsername(magazine.getMainEditor());

        User editor = userDBService.getRandomEditorExcludingMagazineMainEditor(mainEditor);

        delegateExecution.setVariable("editorEmail", editor.getEmail());
        delegateExecution.setVariable("editor", editor.getUsername());
    }

}
