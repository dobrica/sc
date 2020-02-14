package com.upp.service.user;

import com.upp.service.model.User;
import com.upp.service.model.UserDBService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CheckReviewersHandler implements TaskListener {

    @Autowired
    private final UserDBService userDBService;

    public CheckReviewersHandler(final UserDBService userDBService) {
        this.userDBService = userDBService;
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        var recezent1 = delegateTask.getVariable("reviewer1");
        var recezent2 = delegateTask.getVariable("reviewer2");
        var brRecezenata = 0;

        if (!recezent1.equals("")) {
            brRecezenata++;
        }

        if (!recezent2.equals("")) {
            brRecezenata++;
        }

        delegateTask.setVariable("brRecezenata", brRecezenata);
        var reviewer1 = userDBService.findUserByUsername(recezent1.toString());
        var reviewer2 = userDBService.findUserByUsername(recezent2.toString());
        ArrayList<User> reviewers = new ArrayList<>();
        reviewers.add(reviewer1);
        reviewers.add(reviewer2);
        delegateTask.setVariable("reviewers", reviewers);
    }
}
