package com.api.taskManagement.security.service;

import com.api.taskManagement.data.models.Role_Name;
import com.api.taskManagement.data.models.User;
import com.api.taskManagement.data.repository.UserRepository;
import com.api.taskManagement.security.user.SecureUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {
    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Not found");
        }

        return SecureUser.builder()
                .user(user)
                .roles(List.of(Role_Name.ROLE_ADMIN, Role_Name.ROLE_USER))
                .build();
    }
}
