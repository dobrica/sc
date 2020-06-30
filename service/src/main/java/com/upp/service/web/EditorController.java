package com.upp.service.web;

import com.upp.service.camunda.Utils;
import com.upp.service.camunda.model.FormFields;
import com.upp.service.camunda.model.FormSubmission;
import com.upp.service.model.OptionsRepository;
import com.upp.service.model.User;
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

import java.util.*;

@RestController
public class EditorController {

    @Autowired
    FormService formService;

    @Autowired
    TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;

    @GetMapping(value = "/editor/review/{taskId}", produces = "application/json")
    public ResponseEntity<FormFields> getForm(@PathVariable("taskId") String taskId) {

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
        List<User> coauthors = (ArrayList) runtimeService.getVariable(processId, "coauthors");
        for (FormField field : properties) {
            if (field.getId().equals("checkCoauthors")) {
                Map<String, String> enumType = ((EnumFormType) field.getType()).getValues();
                enumType.clear();
                coauthors.forEach(x -> enumType.put(x.getEmail(), x.getFistname() + " " +
                        x.getLastname() + " " + x.getEmail() + " " + x.getCity() + " " + x.getState()));
            }
            if (field.getId().equals("correctionDuration")) {
                Map<String, String> enumType = ((EnumFormType) field.getType()).getValues();
                enumType.clear();
                OptionsRepository.timeOptions.forEach((k, v) -> enumType.put(k, v));
                runtimeService.setVariable(processId,"timeOptions", enumType);
            }
        }
        return new ResponseEntity<>(new FormFields(task.getId(), task.getName(), processId, properties), HttpStatus.OK);
    }

    @PostMapping(value = "/editor/review/scientificPaper/{taskId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<FormFields> postEditorTask(@RequestBody List<FormSubmission> editorTask, @PathVariable("taskId") String taskId){
        HashMap<String, Object> map = Utils.mapListToDto(editorTask);
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
            formService.submitTaskForm(taskId, map);
        return new ResponseEntity<>(new FormFields(task.getId(), task.getName(), processInstanceId, properties), HttpStatus.OK);
    }

}
