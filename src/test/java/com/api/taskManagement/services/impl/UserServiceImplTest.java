//package com.api.taskManagement.services.impl;
//
//import com.api.taskManagement.data.dto.request.UserLoginRequest;
//import com.api.taskManagement.data.dto.request.UserRegistrationRequest;
//import com.api.taskManagement.data.models.User;
//import com.api.taskManagement.data.repository.UserRepository;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@RunWith(MockitoJUnitRunner.class)
//public class UserServiceImplTest {
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    @Test
//    public void loginUser_ValidCredentials_ReturnsJwtToken() {
//        // Prepare test data
//        UserLoginRequest request = new UserLoginRequest();
//        request.setUsername("testuser");
//        request.setPassword("password123");
//
//        User user = new User();
//        user.setUsername("testuser");
//        user.setPassword("$2a$10$R91u..y..9vMA7E65Y2PKOelHfUnFr.GN6bpYOMqG2TRGnlTfYyc.");
//
//        Mockito.when(userRepository.findByUsername(request.getUsername())).thenReturn(user);
//        Mockito.when(passwordEncoder.matches(request.getPassword(), user.getPassword())).thenReturn(true);
//
//        // Execute the method
//        String jwtToken = userService.loginUser(request);
//
//        // Assert the result
//        assertEquals("JWT_TOKEN", jwtToken);
//    }
