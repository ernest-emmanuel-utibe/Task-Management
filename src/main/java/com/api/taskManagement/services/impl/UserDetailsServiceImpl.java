//package com.api.taskManagement.services.impl;
//
//import com.api.taskManagement.data.models.User;
//import com.api.taskManagement.data.models.UserDetails;
//import com.api.taskManagement.data.repository.UserRepository;
//import com.api.taskManagement.exception.UsernameNotFoundException;
//import com.api.taskManagement.services.UserDetailsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
//
//        return UserPrincipal.create(user);
//    }
//}
