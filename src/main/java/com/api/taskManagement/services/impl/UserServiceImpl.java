package com.api.taskManagement.services.impl;

import com.api.taskManagement.data.models.User;
import com.api.taskManagement.data.repository.UserRepository;
import com.api.taskManagement.exception.UsernameAlreadyExistsException;
import com.api.taskManagement.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

//    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(User user) throws UsernameAlreadyExistsException {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UsernameAlreadyExistsException("Username already exists");
        }

        User newUser = new User();
        newUser.setUsername(user.getUsername());
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        newUser.setPassword(hashedPassword);

        userRepository.save(newUser);
    }

}
