package com.upp.service.camunda.model;

import lombok.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FormSubmission implements Serializable{

	String fieldId;
	String fieldValue;

	public HashMap<String, Object> mapListToDto(List<FormSubmission> list)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		for(FormSubmission temp : list){
			map.put(temp.getFieldId(), temp.getFieldValue());
		}

		return map;
	}

}
