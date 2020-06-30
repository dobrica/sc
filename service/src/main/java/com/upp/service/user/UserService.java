package com.upp.service.user;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

public class UserService implements ExecutionListener {

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        var isSubscriptionPaid = delegateExecution.getVariable("subscriptionPaid");
        delegateExecution.setVariable("hasActiveSubscription", isSubscriptionPaid);
    }

}
