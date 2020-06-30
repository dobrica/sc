package com.upp.service.web;

import com.upp.service.camunda.Utils;
import com.upp.service.camunda.model.FormFields;
import com.upp.service.camunda.model.FormSubmission;
import com.upp.service.camunda.model.Task;
import com.upp.service.model.ScientificPaper;
import com.upp.service.model.ScientificPaperDBService;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    ScientificPaperDBService scientificPaperDBService;

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
    public @ResponseBody FormFields getTask(@PathVariable String taskId) {
        if(taskId.equals("newProcess")){
            return new FormFields(null, "newProcess", null, null);
        }

        List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery()
                .active()
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

    @PostMapping(value = "/task/{taskId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<String> submit(@RequestBody List<FormSubmission> request, @PathVariable("taskId") String taskId){
        HashMap<String, Object> map = Utils.mapListToDto(request);
        org.camunda.bpm.engine.task.Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processId = "";

        if (task == null) {
            ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(taskId).singleResult();
            processId = pi.getId();
            task = taskService.createTaskQuery().processInstanceId(taskId).singleResult();
        } else {
            processId = task.getProcessInstanceId();
        }

        String spId = (String) runtimeService.getVariable(processId, "spId");
        if(task.getName().equals("Unesi podatke o naucnom radu") && (spId == null || spId.equals(""))) {
            spId = UUID.randomUUID().toString();
            runtimeService.setVariable(processId, "spId", spId);
        }
        formService.submitTaskForm(taskId, map);
        return new ResponseEntity<String>(spId, HttpStatus.OK);
    }
}
