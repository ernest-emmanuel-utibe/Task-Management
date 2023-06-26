package com.api.taskManagement.data.models;

public class UserPrincipal implements UserDetails{
    private User user;

    public UserPrincipal(User user) {
        this.user = user;
    }
}
