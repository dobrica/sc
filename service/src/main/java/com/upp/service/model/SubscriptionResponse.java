package com.upp.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionResponse {
    //naming: angular def. mapping removes 'is' so it will be openAccess by default in frontend
    private boolean isOpenAccess;
    private boolean membershipStatus;
}
