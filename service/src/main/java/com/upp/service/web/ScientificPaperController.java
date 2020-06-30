package com.upp.service.web;

import com.upp.service.camunda.Utils;
import com.upp.service.camunda.model.FormFields;
import com.upp.service.camunda.model.FormSubmission;
import com.upp.service.model.*;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ScientificPaperController {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    ScientificPaperDBService scientificPaperDBService;

    @Autowired
    TaskService taskService;

    @Autowired
    FormService formService;

    @Autowired
    MagazineDBService magazineDBService;

    @GetMapping(value = "/paperDetails/{taskId}")
    public ResponseEntity<FormFields> getScientificFieldForm(@PathVariable("taskId") String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processId = "";

        if (task == null) {
            ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(taskId).singleResult();
            processId = pi.getId();
            task = taskService.createTaskQuery().processInstanceId(taskId).singleResult();
        } else {
            processId = task.getProcessInstanceId();
        }

        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        Magazine magazine = magazineDBService.findMagazineById((String) runtimeService.getVariable(processId, "magazine"));
        for (FormField field : properties) {
            if (field.getId().equals("scientificField")) {
                Map<String, String> enumType = ((EnumFormType) field.getType()).getValues();
                enumType.clear();
                for (String scienceField : OptionsRepository.scienceFields) {
                    enumType.put(scienceField, scienceField);
                }
            }
        }
        runtimeService.setVariable(processId, "scientificFields", OptionsRepository.scienceFields);
        return new ResponseEntity<>(new FormFields(task.getId(), task.getName(), processId, properties), HttpStatus.OK);
    }

    @GetMapping(value = "/scientificField/options/{taskId}")
    public ResponseEntity<List<String>> getScientificFieldOptions(@PathVariable("taskId") String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        runtimeService.setVariable(processInstanceId, "scientificFields", OptionsRepository.scienceFields);
        return new ResponseEntity<>(OptionsRepository.scienceFields, HttpStatus.OK);
    }

    @PostMapping(value = "/paperDetails/create/{taskId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Boolean> save(@RequestBody List<FormSubmission> paperDetails, @PathVariable("taskId") String taskId) {
        HashMap<String, Object> map = Utils.mapListToDto(paperDetails);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping(value = "/coauthor/add/{taskId}")
    public ResponseEntity<FormFields> getCoauthorAddForm(@PathVariable("taskId") String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processId = "";

        if (task == null) {
            ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(taskId).singleResult();
            processId = pi.getId();
            task = taskService.createTaskQuery().processInstanceId(taskId).singleResult();
        } else {
            processId = task.getProcessInstanceId();
        }
        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        return new ResponseEntity<>(new FormFields(task.getId(), task.getName(), processId, properties), HttpStatus.OK);
    }

    @PostMapping(value = "/coauthor/add/{taskId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Boolean> saveCoauthorAddForm(@RequestBody List<FormSubmission> coauthor, @PathVariable("taskId") String taskId) {
        HashMap<String, Object> map = Utils.mapListToDto(coauthor);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping(value = "/corrections/{taskId}")
    public ResponseEntity<FormFields> authorsCorrectionsForm(@PathVariable("taskId") String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processId = "";

        if (task == null) {
            ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(taskId).singleResult();
            processId = pi.getId();
            task = taskService.createTaskQuery().processInstanceId(taskId).singleResult();
        } else {
            processId = task.getProcessInstanceId();
        }

        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        Magazine magazine = magazineDBService.findMagazineById((String) runtimeService.getVariable(processId, "magazine"));

        return new ResponseEntity<>(new FormFields(task.getId(), task.getName(), processId, properties), HttpStatus.OK);
    }

    @PostMapping(value = "/corrections/apply/{taskId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Boolean> corrections(@RequestBody List<FormSubmission> corrections, @PathVariable("taskId") String taskId) {
        HashMap<String, Object> map = Utils.mapListToDto(corrections);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping(value = "/committee/{taskId}")
    public ResponseEntity<FormFields> committeeForm(@PathVariable("taskId") String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processId = "";

        if (task == null) {
            ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(taskId).singleResult();
            processId = pi.getId();
            task = taskService.createTaskQuery().processInstanceId(taskId).singleResult();
        } else {
            processId = task.getProcessInstanceId();
        }

        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        List<User> coauthors = (ArrayList) runtimeService.getVariable(processId, "coauthors");
        for (FormField field : properties) {
            if (field.getId().equals("reviewDuration")) {
                Map<String, String> enumType = ((EnumFormType) field.getType()).getValues();
                enumType.clear();
                OptionsRepository.timeOptions.forEach((k, v) -> enumType.put(k, v));
                runtimeService.setVariable(processId,"timeOptions", enumType);
            }
        }

        return new ResponseEntity<>(new FormFields(task.getId(), task.getName(), processId, properties), HttpStatus.OK);
    }

    @PostMapping(value = "/committee/create/{taskId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Boolean> committee(@RequestBody List<FormSubmission> committee, @PathVariable("taskId") String taskId) {
        HashMap<String, Object> map = Utils.mapListToDto(committee);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

        formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PutMapping(value = "/scientificPaper/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> savePdf(@RequestParam("file") MultipartFile file , @PathVariable("id") String scientificPaperId){
        scientificPaperDBService.savePdf(scientificPaperId, file);
        return new ResponseEntity<>("Upload successful!", HttpStatus.OK);
    }

}
