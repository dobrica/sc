package com.upp.service.web;

import com.upp.service.camunda.Utils;
import com.upp.service.camunda.model.FormFields;
import com.upp.service.camunda.model.FormSubmission;
import com.upp.service.camunda.model.Task;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
public class TaskController {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    TaskService taskService;

    @Autowired
    FormService formService;

    @Autowired
    IdentityService identityService;

    @GetMapping(path = "/tasks/{username}", produces = "application/json")
    public ResponseEntity<List<Task>> getTasks(@PathVariable String username) {
        List<Task> tasks = new ArrayList<>();
        List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery().active().list();
        for (ProcessInstance p: processInstances) {
            List<org.camunda.bpm.engine.task.Task> processTasks = taskService.createTaskQuery().processInstanceId(p.getId()).list();
            for (org.camunda.bpm.engine.task.Task t: processTasks) {
                if (username.equals(t.getAssignee())){
                    tasks.add(new Task(t.getId(), t.getName(), t.getAssignee()));
                }
            }
        }
        return new ResponseEntity<List<Task>>(tasks, HttpStatus.OK);
    }

    @GetMapping(path = "/task/{taskId}", produces = "application/json")
    public @ResponseBody FormFields getTask(@PathVariable String taskId) { //TODO: make it less complicated
        if(taskId.equals("newProcess")){
            return new FormFields(null, "newProcess", null, null);
        }

        // list all running/unsuspended instances of the process
        List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery()
                //.processDefinitionKey("RegistrationProcess") TODO: in order to optimize search add this
                .active() // we only want the unsuspended process instances
                .list();

        ProcessInstance processInstance = null;
        org.camunda.bpm.engine.task.Task task = null;
        for (ProcessInstance p: processInstances) {
            List<org.camunda.bpm.engine.task.Task> tasks = taskService.createTaskQuery().processInstanceId(p.getId()).list();
            for (org.camunda.bpm.engine.task.Task t: tasks) {
                if (t.getId().equals(taskId)) {
                    task = t;
                    processInstance = p;
                }
            }
        }

        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        for (FormField fp : properties) {
            System.out.println(fp.getId() + fp.getType());
        }

        return new FormFields(task.getId(),task.getName(), processInstance.getId(), properties);
    }

    @PostMapping(value = "/task/{taskId}/submit", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Boolean> submit(@RequestBody List<FormSubmission> paymentConfirmation, @PathVariable("taskId") String taskId){
        HashMap<String, Object> map = Utils.mapListToDto(paymentConfirmation);
        org.camunda.bpm.engine.task.Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
