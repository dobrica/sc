package com.upp.service.web;

import com.upp.service.camunda.model.FormFields;
import com.upp.service.camunda.model.FormSubmission;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
public class MagazineController {

    @Value("${process.new.magazine}")
    private String newMagazineProcess;

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

    @GetMapping(path = "/magazine/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public FormFields get() { // TODO: @ResponseBody ???
        //provera da li korisnik sa id-jem pera postoji //TODO: check?!
        //List<User> users = identityService.createUserQuery().userId("pera").list();
        ProcessInstance pi = runtimeService.startProcessInstanceByKey(newMagazineProcess);

        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);

        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        for (FormField fp : properties) {
            System.out.println(fp.getId() + fp.getType());
        }

        return new FormFields(task.getId(), pi.getId(), properties);
    }

    @PostMapping(path = "/magazine/create/{taskId}", produces = "application/json")
    public @ResponseBody
    ResponseEntity post(@RequestBody List<FormSubmission> dto, @PathVariable String taskId) {
        HashMap<String, Object> map = this.mapListToDto(dto);

        // list all running/unsuspended instances of the process
//		    ProcessInstance processInstance =
//		        runtimeService.createProcessInstanceQuery()
//		            .processDefinitionKey("Process_1")
//		            .active() // we only want the unsuspended process instances
//		            .list().get(0);

//			Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0);

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        runtimeService.setVariable(processInstanceId, "registration", dto);
        formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private HashMap<String, Object> mapListToDto(List<FormSubmission> list)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for(FormSubmission temp : list){
            map.put(temp.getFieldId(), temp.getFieldValue());
        }

        return map;
    }

}
