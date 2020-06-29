package com.upp.service.web;

import com.upp.service.camunda.Utils;
import com.upp.service.camunda.model.FormFields;
import com.upp.service.camunda.model.FormSubmission;
import com.upp.service.magazine.MagazineService;
import com.upp.service.model.Magazine;
import com.upp.service.model.MagazineDBService;
import com.upp.service.model.SubscriptionResponse;
import com.upp.service.security.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    MagazineDBService magazineDBService;

    @Autowired
    TokenUtils tokenUtils;

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

        return new FormFields(task.getId(), task.getName(), pi.getId(), properties);
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

    @GetMapping(value = "/scientificPaper", produces = "application/json")
    public ResponseEntity<FormFields> getForm(HttpServletRequest request){

        ProcessInstance pi = runtimeService.startProcessInstanceByKey("ProcessText");
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);
        String name = Utils.getUsernameFromRequest(request, tokenUtils);
        runtimeService.setVariable(pi.getId(), "username", name);
        taskService.setAssignee(task.getId(), name);
        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();

        List<Magazine> magazines = magazineDBService.findAllMagazines();
        for(FormField field : properties){
            if(field.getId().equals("magazine")){
                Map<String, String> enumType = ((EnumFormType) field.getType()).getValues();
                enumType.clear();
                for(Magazine m: magazines){
                    enumType.put(m.getIssn(), m.getTitle());
                }
                runtimeService.setVariable(pi.getId(),"magazines", enumType);
                break;
            }
        }
        return new ResponseEntity<>(new FormFields(task.getId(), task.getName(), pi.getId(), properties), HttpStatus.OK);
    }

    @GetMapping(value = "/scientificPaper/{taskId}", produces = "application/json")
    public ResponseEntity<FormFields> getForm(@PathVariable("taskId") String taskId, HttpServletRequest request){

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processId = task.getProcessInstanceId();
        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();

        List<Magazine> magazines = magazineDBService.findAllMagazines();
        for(FormField field : properties){
            if(field.getId().equals("magazine")){
                Map<String, String> enumType = ((EnumFormType) field.getType()).getValues();
                enumType.clear();
                for(Magazine m: magazines){
                    enumType.put(m.getIssn(), m.getTitle());
                }
                runtimeService.setVariable(processId,"magazines", enumType);
                break;
            }
        }
        return new ResponseEntity<>(new FormFields(task.getId(),task.getName(), processId, properties), HttpStatus.OK);
    }

    @PostMapping(value = "/chooseMagazine/{taskId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<SubscriptionResponse> selectMagazine(@RequestBody List<FormSubmission> magazine, @PathVariable("taskId") String taskId, HttpServletRequest request){
        HashMap<String, Object> map = Utils.mapListToDto(magazine);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        formService.submitTaskForm(taskId, map);
        boolean isOpenAccess = (boolean) runtimeService.getVariable(processInstanceId, "isOpenAccess");
//        boolean membershipStatus = (boolean) runtimeService.getVariable(processInstanceId, "status");
        SubscriptionResponse response = new SubscriptionResponse(isOpenAccess, false);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/chooseReviewers/{taskId}")
    public ResponseEntity<FormFields> getChooseReviewersForm(@PathVariable("taskId") String taskId){
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        List<String> reviewerOptions = new ArrayList<>();
        reviewerOptions.add("djordje");
        reviewerOptions.add("zika");
        for(FormField field : properties){
            if(field.getId().equals("reviewer")){
                Map<String, String> enumType = ((EnumFormType) field.getType()).getValues();
                enumType.clear();
                for(String r: reviewerOptions){
                    enumType.put(r, r);
                }
                runtimeService.setVariable(processInstanceId,"reviewerOptions", enumType);
                break;
            }
        }
        return new ResponseEntity<>( new FormFields(task.getId(),task.getName(), processInstanceId, properties), HttpStatus.OK);
    }

    @PostMapping(value = "/chooseReviewers/create/{taskId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Boolean> chooseReviewers(@RequestBody List<FormSubmission> chooseReviewers, @PathVariable("taskId") String taskId) {
        HashMap<String, Object> map = Utils.mapListToDto(chooseReviewers);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

        formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping(value = "/review/{taskId}")
    public ResponseEntity<FormFields> review(@PathVariable("taskId") String taskId){
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        List<String> reviewOptions = new ArrayList<>();
        reviewOptions.add("Predlazem da rad bude prihvacen!");
        reviewOptions.add("Predlazem da rad bude odbijen!");
        reviewOptions.add("Predlazem da rad bude prihvacen uz manje izmene!");
        reviewOptions.add("Predlazem da rad bude prihvacen uz vece izmene!");
        for(FormField field : properties){
            if(field.getId().equals("recommendation")){
                Map<String, String> enumType = ((EnumFormType) field.getType()).getValues();
                enumType.clear();
                for(String r: reviewOptions){
                    enumType.put(r, r);
                }
                runtimeService.setVariable(processInstanceId,"reviewOptions", enumType);
                break;
            }
        }
        return new ResponseEntity<>( new FormFields(task.getId(),task.getName(), processInstanceId, properties), HttpStatus.OK);
    }

    @PostMapping(value = "/review/create/{taskId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Boolean> review(@RequestBody List<FormSubmission> review, @PathVariable("taskId") String taskId) {
        HashMap<String, Object> map = Utils.mapListToDto(review);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

        formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(true, HttpStatus.OK);
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
