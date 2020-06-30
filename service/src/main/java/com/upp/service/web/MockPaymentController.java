package com.upp.service.web;

import com.upp.service.camunda.Utils;
import com.upp.service.camunda.model.FormFields;
import com.upp.service.camunda.model.FormSubmission;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class MockPaymentController {

    @Autowired
    FormService formService;

    @Autowired
    TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;

    @GetMapping(value = "/payment/{taskId}", produces = "application/json")
    public ResponseEntity<FormFields> getForm(@PathVariable("taskId") String taskId){
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        ProcessInstance subprocess = runtimeService.createProcessInstanceQuery().superProcessInstanceId(processInstanceId).singleResult();
        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        return new ResponseEntity<>(new FormFields(task.getId(), task.getName(), processInstanceId, properties), HttpStatus.OK);
    }

    @PostMapping(value = "/payment/create/{taskId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Boolean> payment(@RequestBody List<FormSubmission> paymentConfirmation, @PathVariable("taskId") String taskId){
        HashMap<String, Object> map = Utils.mapListToDto(paymentConfirmation);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
