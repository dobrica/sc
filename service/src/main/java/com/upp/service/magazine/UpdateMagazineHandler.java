package com.upp.service.magazine;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UpdateMagazineHandler implements JavaDelegate {

    @Autowired
    IdentityService identityService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.info("Updating magazine: {}", "TEST_MAGAZINE"); //TODO: print issn
    }

}
