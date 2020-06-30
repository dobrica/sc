package com.upp.service.magazine;

import com.upp.service.model.Magazine;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
public class CreateMagazineHandler implements JavaDelegate {

    @Autowired
    IdentityService identityService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Magazine magazine = new Magazine();
        magazine.setIssn(String.valueOf(new Random().nextInt(8)));
        delegateExecution.setVariable("mainEditor", "demo");
        log.info("Creating new magazine: {}", magazine.getIssn());
    }

}
