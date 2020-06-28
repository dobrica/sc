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
        ArrayList<User> reviewers = (ArrayList<User>) delegateTask.getVariable("reviewers");
        if (reviewers == null) {
            reviewers = new ArrayList<>();
        }

        String reviewerUsername = (String) delegateTask.getVariable("reviewer");
        User reviewer = userDBService.findUserByUsername(reviewerUsername);
        reviewers.add(reviewer);
        delegateTask.setVariable("reviewers", reviewers);
        delegateTask.setVariable("reviewersNumber", reviewers.size());
    }
}
