package com.upp.service.payment;

import com.upp.service.model.User;
import com.upp.service.model.UserDBService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentHandler implements JavaDelegate {

    @Autowired
    UserDBService userDBService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String username = (String) delegateExecution.getVariable("username");
        User user = userDBService.findUserByUsername(username);
        user.setSubscriptionStatus(true);
        userDBService.saveUser(user);
    }

}
