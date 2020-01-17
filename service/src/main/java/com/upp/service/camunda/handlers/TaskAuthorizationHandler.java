package com.upp.service.camunda.handlers;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.AuthorizationService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.authorization.Authorization;
import org.camunda.bpm.engine.authorization.Permission;
import org.camunda.bpm.engine.authorization.Permissions;
import org.camunda.bpm.engine.authorization.Resources;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.rest.dto.identity.UserQueryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TaskAuthorizationHandler implements TaskListener {

    @Autowired
    IdentityService identityService;

    @Override
    public void notify(DelegateTask delegateTask) {
        String assignee = delegateTask.getAssignee();
        log.info("assignee: " + assignee);

        var userGroups = identityService.createGroupQuery().groupMember(assignee).list();
        for (var group: userGroups) {
            if (group.getId().equals("urednici")){
            }
        }
//        AuthorizationService authorizationService = delegateTask.getProcessEngineServices().getAuthorizationService();
//        Authorization authorization = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_REVOKE);
//
//        Permission[] permissions = new Permission[1];
//        permissions[0] = Permissions.ALL;
//
//        authorization.setUserId(initiator);
//        authorization.setResource(Resources.TASK);
//        authorization.setResourceId(delegateTask.getId());
//        authorization.setPermissions(permissions);
//
//        authorizationService.saveAuthorization(authorization);
    }
}
