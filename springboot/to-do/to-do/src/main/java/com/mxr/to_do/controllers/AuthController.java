package com.mxr.to_do.controllers;

import com.mxr.to_do.dtos.LoginRequest;
import com.mxr.to_do.dtos.LoginResponse;
import com.mxr.to_do.dtos.UserRequest;
import com.mxr.to_do.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;
    
    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@Valid @RequestBody UserRequest userRequest) {
        LoginResponse response = authService.register(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }
}
