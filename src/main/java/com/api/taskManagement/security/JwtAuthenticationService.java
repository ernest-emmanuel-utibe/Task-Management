package com.api.taskManagement.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JwtAuthenticationService {

    private final JwtTokenProvider tokenProvider;
    private final CustomUserDetailsService userDetailsService;

    @Autowired
    public JwtAuthenticationService(JwtTokenProvider tokenProvider, CustomUserDetailsService userDetailsService) {
        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
    }

    public String refreshToken(String refreshToken) {
        if (!tokenProvider.validateToken(refreshToken)) {
            return null;
        }

        String username = tokenProvider.getUsernameFromToken(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        return tokenProvider.generateToken(userDetails);
    }
}
