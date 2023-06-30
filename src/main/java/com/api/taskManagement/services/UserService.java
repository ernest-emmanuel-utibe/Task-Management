package com.api.taskManagement.services;

import com.api.taskManagement.data.models.User;
import com.api.taskManagement.exception.UsernameAlreadyExistsException;

public interface UserService {
    void registerUser(User user) throws UsernameAlreadyExistsException;

}
