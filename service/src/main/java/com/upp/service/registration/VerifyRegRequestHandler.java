package com.upp.service.registration;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class VerifyRegRequestHandler implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        delegateExecution.setVariable("isRegistrationConfirmed",
                delegateExecution.getVariable("verificationCode").
                        equals(delegateExecution.getVariable("userVerificationCode")));
    }

}
