package com.upp.service.user;

import com.upp.service.model.User;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AddCoauthorHandler implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        ArrayList<User> coauthors = (ArrayList<User>) delegateExecution.getVariable("coauthors");
        if (coauthors == null) {
            coauthors = new ArrayList<>();
        }

        String firstname = (String) delegateExecution.getVariable("ca_name");
        String lastname = (String) delegateExecution.getVariable("ca_surname");
        String email = (String) delegateExecution.getVariable("ca_email");
        String city = (String) delegateExecution.getVariable("ca_city");
        String state = (String) delegateExecution.getVariable("ca_state");

        User coauthor = new User();
        coauthor.setFistname(firstname);
        coauthor.setLastname(lastname);
        coauthor.setEmail(email);
        coauthor.setCity(city);
        coauthor.setState(state);

        coauthors.add(coauthor);

        ObjectValue typedCustomerValue = Variables.objectValue(coauthors).serializationDataFormat(Variables.SerializationDataFormats.JAVA).create();
        delegateExecution.setVariable("coauthors", coauthors);

        // reset fields
        delegateExecution.setVariable("ca_name", "");
        delegateExecution.setVariable("ca_surname", "");
        delegateExecution.setVariable("ca_email", "");
        delegateExecution.setVariable("ca_city", "");
        delegateExecution.setVariable("ca_state", "");
    }

}
