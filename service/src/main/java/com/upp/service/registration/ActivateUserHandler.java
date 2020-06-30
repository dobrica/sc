package com.upp.service.registration;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivateUserHandler implements JavaDelegate {

    @Autowired
    IdentityService identityService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String username = getVariableAsString(delegateExecution.getVariable("username"));
        User user = identityService.newUser(username);
        user.setId(username);
        user.setPassword(getVariableAsString(delegateExecution.getVariable("password")));
        user.setEmail(getVariableAsString(delegateExecution.getVariable("email")));
        user.setFirstName(getVariableAsString(delegateExecution.getVariable("firstname")));
        user.setLastName(getVariableAsString(delegateExecution.getVariable("lastname")));
        identityService.saveUser(user);
    }

    public String getVariableAsString(Object executionVariable) {
        return String.valueOf(executionVariable);
    }
}
