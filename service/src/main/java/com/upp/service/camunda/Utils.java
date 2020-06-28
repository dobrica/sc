package com.upp.service.camunda;



import com.upp.service.camunda.model.FormSubmission;
import com.upp.service.security.TokenUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

public class Utils {
	
	
    public static HashMap<String, Object> mapListToDto(List<FormSubmission> list)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for(FormSubmission temp : list){
            map.put(temp.getFieldId(), temp.getFieldValue());
        }

        return map;
    }

    public static String getUsernameFromRequest(HttpServletRequest request, TokenUtils tokenUtils) {
        String authToken = tokenUtils.getToken(request);
        if (authToken == null) {
            return null;
        }
        String username = tokenUtils.getUsernameFromToken(authToken);
        return username;
    }

    public static String getFormFieldValue(List<FormSubmission> list, String name){
        for(FormSubmission dto: list){
            if(dto.getFieldId().equals(name)){
                return dto.getFieldValue();
            }
        }
        return null;
    }
}
