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
//        Boolean paymentStatus = (Boolean) delegateExecution.getVariable("isPaymentSuccessful");
//        String username = (String) delegateExecution.getProcessInstance().getVariable("username");
        Boolean paymentStatus = true;
        String username = "perica";
        User user = userDBService.findUserByUsername(username);
        user.setSubscriptionStatus(paymentStatus);
        userDBService.saveUser(user);
    }

}
