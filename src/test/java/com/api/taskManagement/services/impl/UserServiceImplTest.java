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
//    @InjectMocks
//    private UserServiceImpl userService;
//
//
//    @Test
//    public void registerUser_NewUsername_SuccessfullyRegistersUser() {
//        // Prepare test data
//        UserRegistrationRequest request = new UserRegistrationRequest();
//        request.setUsername("testuser");
//        request.setPassword("password123");
//
//        Mockito.when(userRepository.findByUsername(request.getUsername())).thenReturn(null);
//
//        // Execute the method
//        userService.registerUser(request);
//
//        // Verify the method calls
//        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(User.class));
//    }
//
//    @Test
//    public void registerUser_ExistingUsername_ThrowsException() {
//        // Prepare test data
//        UserRegistrationRequest request = new UserRegistrationRequest();
//        request.setUsername("testuser");
//        request.setPassword("password123");
//
//        User existingUser = new User();
//        existingUser.setUsername("testuser");
//
//        Mockito.when(userRepository.findByUsername(request.getUsername())).thenReturn(existingUser);
//
//        // Execute and assert
//        assertThrows(RuntimeException.class, () -> userService.registerUser(request));
//    }
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
//
//    @Test
//    public void loginUser_InvalidUsername_ThrowsException() {
//        // Prepare test data
//        UserLoginRequest request = new UserLoginRequest();
//        request.setUsername("testuser");
//        request.setPassword("password123");
//
//        Mockito.when(userRepository.findByUsername(request.getUsername())).thenReturn(null);
//
//        // Execute and assert
//        assertThrows(RuntimeException.class, () -> userService.loginUser(request));
//    }
//
//
//        // Execute and assert
//        assertThrows(RuntimeException.class, () -> userService.loginUser(request));
//    }
//}
