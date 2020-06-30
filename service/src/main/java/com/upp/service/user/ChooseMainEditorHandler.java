package com.upp.service.user;

import com.upp.service.model.MagazineDBService;
import com.upp.service.model.User;
import com.upp.service.model.UserDBService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChooseMainEditorHandler implements JavaDelegate {

    @Autowired
    private final MagazineDBService magazineDBService;

    @Autowired
    private final UserDBService userDBService;

    public ChooseMainEditorHandler(final MagazineDBService magazineDBService,
                       final UserDBService userDBService) {
        this.magazineDBService = magazineDBService;
        this.userDBService = userDBService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        var mId = delegateExecution.getVariable("magazine");
        var magazine = magazineDBService.findMagazineById(mId.toString());
        User mainEditor = userDBService.findUserByUsername(magazine.getMainEditor());
        var author = userDBService.findUserByUsername((String) delegateExecution.getVariable("username"));
        delegateExecution.setVariable("autorEmail", author.getEmail());
        delegateExecution.setVariable("mainEditor", mainEditor.getUsername());
        delegateExecution.setVariable("mainEditorEmail", mainEditor.getEmail());
    }

}
