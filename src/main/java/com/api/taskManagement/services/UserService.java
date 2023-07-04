package com.api.taskManagement.services;

import com.api.taskManagement.data.dto.request.UserLoginRequest;
import com.api.taskManagement.data.dto.request.UserRegistrationRequest;

public interface UserService {
//    void registerUser(User user) throws UsernameAlreadyExistsException;

    void registerUser(UserRegistrationRequest request);
    String loginUser(UserLoginRequest request);
}
