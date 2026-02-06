package com.mxr.to_do.services;

import com.mxr.to_do.dtos.LoginRequest;
import com.mxr.to_do.dtos.LoginResponse;
import com.mxr.to_do.dtos.UserRequest;
import com.mxr.to_do.entities.User;
import com.mxr.to_do.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UserRepository userRepository;
    
    public LoginResponse register(UserRequest userRequest) {
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        
        String encodedPassword = PasswordEncoder.encode(userRequest.getPassword());
        
        User user = User.builder()
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .password(encodedPassword)
                .build();
        
        User savedUser = userRepository.save(user);
        
        return new LoginResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                "Registration successful"
        );
    }
    
    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));
        
        if (!PasswordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }
        
        return new LoginResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                "Login successful"
        );
    }
}
