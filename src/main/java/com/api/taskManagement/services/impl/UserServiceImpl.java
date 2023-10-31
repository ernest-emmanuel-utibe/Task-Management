package com.api.taskManagement.services.impl;

import com.api.taskManagement.data.dto.request.UserLoginRequest;
import com.api.taskManagement.data.dto.request.UserRegistrationRequest;
import com.api.taskManagement.data.models.User;
import com.api.taskManagement.data.repository.UserRepository;
import com.api.taskManagement.services.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void registerUser(UserRegistrationRequest request) {
        // Check if the username is already taken
        if (userRepository.findByUsername(request.getUsername()) != null) {
            throw new RuntimeException("Username already exists");
        }

        // Create a new User entity
        User user = new User();
        user.setUsername(request.getUsername());
        // Hash the password before storing it in the database
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(hashedPassword);

        // Save the user to the database
        userRepository.save(user);
    }


    @Override
    public String loginUser(UserLoginRequest request) {
        // Retrieve user from the database based on the username
        User user = userRepository.findByUsername(request.getUsername());
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // Check if the provided password matches the stored password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // Generate JWT token
        String token = generateJwtToken(user.getUsername());

        return token;
    }


    private String generateJwtToken(String username) {
        // Set expiration time for the token (e.g., 1 hour)
        long expirationTimeInMillis = 60 * 60 * 1000;
        Date expiryDate = new Date(System.currentTimeMillis() + expirationTimeInMillis);

        // Create claims for the token
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("scopes", Arrays.asList("ROLE_USER"));

        // Generate the token
        String token = Jwts.builder()
                .setClaims(claims)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, "yourSecretKey") // Replace "yourSecretKey" with your own secret key
                .compact();

        return token;
    }

}
