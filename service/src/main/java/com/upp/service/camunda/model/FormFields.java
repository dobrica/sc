package com.upp.service.camunda.model;

import lombok.*;
import org.camunda.bpm.engine.form.FormField;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FormFields {

	String taskId;
	String taskName;
	String processInstanceId;
	List<FormField> formFields;

}
