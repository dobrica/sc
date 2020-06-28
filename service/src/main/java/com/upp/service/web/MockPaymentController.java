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

    @GetMapping(value = "/payment/{pid}", produces = "application/json")
    public ResponseEntity<FormFields> getForm(@PathVariable("pid") String pid){
        ProcessInstance subprocess = runtimeService.createProcessInstanceQuery().superProcessInstanceId(pid).singleResult();
        Task task = taskService.createTaskQuery().processInstanceId(subprocess.getId()).list().get(0);
        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        return new ResponseEntity<>(new FormFields(task.getId(), task.getName(), pid, properties), HttpStatus.OK);
    }

    @PostMapping(value = "/payment/create/{taskId}/{processId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Boolean> payment(@RequestBody List<FormSubmission> paymentConfirmation, @PathVariable("taskId") String taskId, @PathVariable("processId") String processId){
        HashMap<String, Object> map = Utils.mapListToDto(paymentConfirmation);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        Boolean paymentStatus = true; // get from req. is true or false paymentConfirmation
        runtimeService.setVariable(processId,"isPaymentSuccessful", paymentStatus);
        formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
