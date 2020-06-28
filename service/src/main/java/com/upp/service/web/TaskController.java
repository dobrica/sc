package com.upp.service.web;

import com.upp.service.camunda.model.FormFields;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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

    @GetMapping(path = "/tasks/{processInstanceId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Task>> get(@PathVariable String processInstanceId) {

        List<org.camunda.bpm.engine.task.Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
        List<Task> response = new ArrayList<Task>();
        for (org.camunda.bpm.engine.task.Task task : tasks) {
            Task t = new Task(task.getId(), task.getName(), task.getAssignee());
            response.add(t);
        }

        return new ResponseEntity(response,  HttpStatus.OK);
    }

    @GetMapping(path = "/tasks", produces = "application/json")
    public ResponseEntity<List<Task>> getTasks() {
        List<Task> tasks = new ArrayList<>();
        List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery().active().list();
        for (ProcessInstance p: processInstances) {
            List<org.camunda.bpm.engine.task.Task> processTasks = taskService.createTaskQuery().processInstanceId(p.getId()).list();
            for (org.camunda.bpm.engine.task.Task t: processTasks) {
                tasks.add(new Task(t.getId(), t.getName(), t.getAssignee()));
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
}
