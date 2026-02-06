package com.mxr.to_do.dtos;

import lombok.Data;

@Data
public class LoginResponse {
    
    private Long userId;
    private String username;
    private String email;
    private String message;
    
    public LoginResponse(Long userId, String username, String email, String message) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.message = message;
    }
}
