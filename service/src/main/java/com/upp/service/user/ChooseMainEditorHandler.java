package com.upp.service.user;

import com.upp.service.model.Magazine;
import com.upp.service.model.MagazineDBService;
import com.upp.service.model.User;
import com.upp.service.model.UserDBService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        //TODO: send mail, get logged in autor email mail 1, main editor mail 2
        delegateExecution.setVariable("autorEmail", "dobrica21@gmail.com");
        delegateExecution.setVariable("mainEditorEmail", mainEditor.getEmail());
    }

    public List<User> getFreeEditors() {
        var magazines = magazineDBService.findAllMagazines();
        var users = userDBService.findAllUsers();
        var occupiedEditors = new ArrayList<String>();
        for (Magazine m: magazines) {
            occupiedEditors.add(m.getMainEditor());
        }

        users.removeIf(u -> occupiedEditors.contains(u.getUsername()));
        return users;
    }

}
