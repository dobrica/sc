package com.upp.service.web;

import com.upp.service.camunda.Utils;
import com.upp.service.camunda.model.FormFields;
import com.upp.service.camunda.model.FormSubmission;
import com.upp.service.model.Magazine;
import com.upp.service.model.MagazineDBService;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ScientificPaperController {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    FormService formService;

    @Autowired
    MagazineDBService magazineDBService;

    @GetMapping(value = "/paperDetails/{taskId}")
    public ResponseEntity<FormFields> getScientificFieldForm(@PathVariable("taskId") String taskId){
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processId = "";

        if (task == null) {
            ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(taskId).singleResult();
            processId = pi.getId();
            task = taskService.createTaskQuery().processInstanceId(taskId).singleResult();
        }else {
            processId = task.getProcessInstanceId();
        }

        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        Magazine magazine = magazineDBService.findMagazineById((String)runtimeService.getVariable(processId,"magazine"));
        List<String> scienceFields = new ArrayList<>();
        scienceFields.add("Matematika");
        scienceFields.add("Fizika");
        scienceFields.add("Hemija");
        for(FormField field : properties){
            if(field.getId().equals("scientificField")){
                Map<String, String> enumType = ((EnumFormType) field.getType()).getValues();
                enumType.clear();
                for(String scienceField: scienceFields){
                    enumType.put(scienceField, scienceField);
                }
            }
        }
        runtimeService.setVariable(processId,"scientificFields", scienceFields);
        return new ResponseEntity<>(new FormFields(task.getId(),task.getName(), processId, properties), HttpStatus.OK);
    }

    @GetMapping(value = "/scientificField/options/{taskId}")
    public ResponseEntity<List<String>> getScientificFieldOptions(@PathVariable("taskId") String taskId){
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        List<String> scientificFields = new ArrayList<>();
        scientificFields.add("Matematika");
        scientificFields.add("Fizika");
        scientificFields.add("Hemija");
        runtimeService.setVariable(processInstanceId,"scientificFields", scientificFields);
        return new ResponseEntity<>(scientificFields, HttpStatus.OK);
    }

    @PostMapping(value = "/paperDetails/create/{taskId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Boolean> save(@RequestBody List<FormSubmission> paperDetails, @PathVariable("taskId") String taskId){
        HashMap<String, Object> map = Utils.mapListToDto(paperDetails);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
//        SciencePaper sciencePaper = new SciencePaper();
//        sciencePaper.setPdfName(Utils.getFormFieldValue(sciencePaperData, "pdf"));
//        sciencePaper = sciencePaperService.save(sciencePaper);

//        runtimeService.setVariable(processInstanceId, "sciencePaperData", sciencePaperData);
//        runtimeService.setVariable(processInstanceId, "sciencePaperId", sciencePaper.getId());
//        runtimeService.setVariable(processInstanceId, "coauthorList", new ArrayList<Coauthor>());

        formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping(value = "/coauthor/add/{taskId}")
    public ResponseEntity<FormFields> getCoauthorAddForm(@PathVariable("taskId") String taskId){
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processId = "";

        if (task == null) {
            ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(taskId).singleResult();
            processId = pi.getId();
            task = taskService.createTaskQuery().processInstanceId(taskId).singleResult();
        }else {
            processId = task.getProcessInstanceId();
        }
        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        return new ResponseEntity<>(new FormFields(task.getId(),task.getName(), processId, properties), HttpStatus.OK);
    }

    @PostMapping(value = "/coauthor/add/{taskId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Boolean> saveCoauthorAddForm(@RequestBody List<FormSubmission> coauthor, @PathVariable("taskId") String taskId){
        HashMap<String, Object> map = Utils.mapListToDto(coauthor);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

}
