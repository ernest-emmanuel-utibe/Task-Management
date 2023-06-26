package com.api.taskManagement.services;

import com.api.taskManagement.data.models.UserDetails;
import com.api.taskManagement.exception.UsernameNotFoundException;

public interface UserDetailsService {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
