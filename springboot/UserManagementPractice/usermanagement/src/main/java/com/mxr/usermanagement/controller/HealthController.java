package com.mxr.usermanagement.controller;

import com.mxr.usermanagement.config.UserServiceProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    private final UserServiceProperties;
    public HealthController(UserServiceProperties userServiceProperties) {
        this.userServiceProperties = userServiceProperties;
    }
    
}
