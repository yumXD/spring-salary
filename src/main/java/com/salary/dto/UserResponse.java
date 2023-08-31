package com.salary.dto;

import com.salary.domain.User;
import lombok.Getter;

@Getter
public class UserResponse {

    private String username;
    private String email;

    public UserResponse(User user) {
        this.username = user.getActualUsername();
        this.email = user.getEmail();
    }
}