package com.upp.service.payment;

import com.upp.service.model.Magazine;
import com.upp.service.model.MagazineDBService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PreparePaymentHandler implements JavaDelegate {

    @Autowired
    MagazineDBService magazineDBService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String magazineIssn = (String) delegateExecution.getProcessInstance().getVariable("magazine");
        Magazine magazine = magazineDBService.findMagazineById(magazineIssn);
        int fee = magazine.getSubscriptionFeeAmount();
        delegateExecution.setVariable("subscriptionFee", fee);
    }

}
