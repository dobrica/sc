package com.upp.service.web;

import com.upp.service.camunda.model.FormFields;
import com.upp.service.camunda.model.FormSubmission;
import com.upp.service.registration.RegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.Execution;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
public class RegistrationController {

    @Value("${process.registration}")
    private String registrationProcess;

    @Value("${registration.success.endpoint}")
    private String registrationSuccess;

    private final RegistrationService registrationService;

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

    @Autowired
    public RegistrationController(final RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping(path = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody FormFields get() {
        ProcessInstance pi = runtimeService.startProcessInstanceByKey(registrationProcess);

        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);

        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        for (FormField fp : properties) {
            System.out.println(fp.getId() + fp.getType());
        }

        return new FormFields(task.getId(), task.getName(), pi.getId(), properties);
    }

    @PostMapping(path = "/register/{taskId}", produces = "application/json")
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

    @GetMapping(path = "/verify/{processInstanceId}/{hashcode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RedirectView verifyRegistration(@PathVariable String processInstanceId, @PathVariable String hashcode) {
        Execution execution = runtimeService.createExecutionQuery()
                .processInstanceId(processInstanceId).activityId("verifikacija").singleResult();
        runtimeService.setVariable(processInstanceId, "userVerificationCode", hashcode);
        runtimeService.signal(execution.getId());
        return new RedirectView(registrationSuccess);
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
