package com.upp.service.magazine;

import com.upp.service.model.MagazineDBService;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.impl.form.FormFieldImpl;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Service
public class MagazineService implements ExecutionListener, TaskListener {

    @Autowired
    IdentityService identityService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    FormService formService;

    @Autowired
    ProcessEngine processEngine;

    @Autowired
    private final MagazineDBService magazineDBService;

    public MagazineService(final MagazineDBService magazineService) {
        this.magazineDBService = magazineService;
    }

    public void newMagazine() {
        log.info("New magazine...");
    }

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        var mId = delegateExecution.getVariable("magazine");
        var magazine = magazineDBService.findMagazineById(mId.toString());

        delegateExecution.setVariable("isOpenAccess", magazine.getIsOpenAccess());

        delegateExecution.setVariable("hasActiveSubscription", true);
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        FormFieldImpl f = (FormFieldImpl) formService.getTaskFormData(delegateTask.getId()).getFormFields().iterator().next();
    }
}
