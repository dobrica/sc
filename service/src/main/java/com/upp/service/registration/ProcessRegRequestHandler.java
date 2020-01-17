package com.upp.service.registration;

import com.upp.service.camunda.model.FormSubmission;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class ProcessRegRequestHandler implements JavaDelegate {

    @Value("${base.url}")
    private String baseUrl;

    @Value("${verify.registration.endpoint}")
    private String verifyEndpoint;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.info("Registration request processing...");

        List<FormSubmission> registration = (List<FormSubmission>) delegateExecution.getVariable("registration");
        for (FormSubmission formField : registration) {

            if (formField.getFieldId().equals("email")) {
                String verificationCode = String.valueOf(formField.getFieldValue().hashCode());
                delegateExecution.setVariable("verificationCode", verificationCode);
                delegateExecution.setVariable("verificationLink",
                        baseUrl + verifyEndpoint +
                                "/" + delegateExecution.getProcessInstanceId() + "/" + verificationCode);
            }
            if (formField.getFieldId().equals("scientificFields")) {
                String[] scientificFields = Arrays.stream(formField.getFieldValue()
                        .split(",")).map(String::trim).toArray(String[]::new);
                delegateExecution.setVariable("scientificFields", scientificFields);
            }
        }
    }

}
