package com.api.taskManagement.data.dto.request;

import lombok.Data;

@Data
public class UserRegistrationRequest {
    private String username;
    private String password;
}
