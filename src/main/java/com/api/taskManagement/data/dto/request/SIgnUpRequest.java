package com.api.taskManagement.data.dto.request;

import com.api.taskManagement.data.models.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SIgnUpRequest {
    private String firstName;
    private String lastName;
    private String password;
    private String confirmPassword;
    private String phoneNumber;
    private String email;
    private Role role;
}
