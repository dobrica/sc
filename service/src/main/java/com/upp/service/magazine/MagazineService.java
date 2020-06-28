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
//        Task task = taskService.createTaskQuery().processInstanceId(delegateExecution.getProcessInstanceId()).list().get(0);
//        Task task = processEngine.getTaskService().createTaskQuery()
//                .active()
//                .taskName("Task_109q4rs")
//                .processInstanceId(delegateExecution.getProcessInstanceId())
//                .singleResult();

        var mId = delegateExecution.getVariable("magazine");
        var magazine = magazineDBService.findMagazineById(mId.toString());

        delegateExecution.setVariable("isOpenAccess", magazine.getIsOpenAccess());

        delegateExecution.setVariable("hasActiveSubscription", true); //TODO: check user subscription
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        FormFieldImpl f = (FormFieldImpl) formService.getTaskFormData(delegateTask.getId()).getFormFields().iterator().next();
//        Task t = taskService.createTaskQuery().taskId(delegateTask.getId()).singleResult();
//        Map<String, String> values = new LinkedHashMap<String, String>();
//
//        Map<String, String> map = new LinkedHashMap<>();
//        map.put("vv12345", "magazine1");
//        map.put("vv54321", "magazine2");
//        map.put("vv45123", "magazine3");
//
//        ObjectValue typedCustomerValue = Variables.objectValue(map).serializationDataFormat(Variables.SerializationDataFormats.JAVA).create();
//
//        f.setId("magazine");
//        f.setLabel("Magazine");
//        f.setType(new EnumFormType(map)); // TODO: fix doesn't persist
//        f = (FormFieldImpl) formService.getTaskFormData(delegateTask.getId()).getFormFields().iterator().next();
//        delegateTask.setVariable("magazines", typedCustomerValue);

    }
}
