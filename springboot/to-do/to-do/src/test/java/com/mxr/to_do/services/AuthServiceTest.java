package com.mxr.to_do.services;

import com.mxr.to_do.dtos.LoginRequest;
import com.mxr.to_do.dtos.LoginResponse;
import com.mxr.to_do.dtos.UserRequest;
import com.mxr.to_do.entities.User;
import com.mxr.to_do.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    private UserRequest userRequest;
    private LoginRequest loginRequest;
    private User user;

    @BeforeEach
    void setUp() {
        userRequest = new UserRequest();
        userRequest.setName("Test User");
        userRequest.setUsername("testuser");
        userRequest.setEmail("test@example.com");
        userRequest.setPassword("password123");

        loginRequest = new LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("password123");

        user = User.builder()
                .id(1L)
                .name("Test User")
                .username("testuser")
                .email("test@example.com")
                .password("encodedPassword")
                .build();
    }

    @Test
    void register_ShouldReturnLoginResponse_WhenUserIsNew() {
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        LoginResponse response = authService.register(userRequest);

        assertNotNull(response);
        assertEquals(1L, response.getUserId());
        assertEquals("testuser", response.getUsername());
        assertEquals("test@example.com", response.getEmail());
        assertEquals("Registration successful", response.getMessage());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void register_ShouldThrowException_WhenUsernameExists() {
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> authService.register(userRequest));
        assertEquals("Username already exists", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void register_ShouldThrowException_WhenEmailExists() {
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> authService.register(userRequest));
        assertEquals("Email already exists", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void login_ShouldReturnLoginResponse_WhenCredentialsAreValid() {
        String encodedPassword = PasswordEncoder.encode("password123");
        user.setPassword(encodedPassword);

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        LoginResponse response = authService.login(loginRequest);

        assertNotNull(response);
        assertEquals(1L, response.getUserId());
        assertEquals("testuser", response.getUsername());
        assertEquals("test@example.com", response.getEmail());
        assertEquals("Login successful", response.getMessage());
        verify(userRepository).findByUsername("testuser");
    }

    @Test
    void login_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> authService.login(loginRequest));
        assertEquals("Invalid username or password", exception.getMessage());
        verify(userRepository).findByUsername("testuser");
    }

    @Test
    void login_ShouldThrowException_WhenPasswordIsInvalid() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> authService.login(loginRequest));
        assertEquals("Invalid username or password", exception.getMessage());
        verify(userRepository).findByUsername("testuser");
    }

    @Test
    void register_ShouldEncodePassword() {
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            assertNotEquals("password123", savedUser.getPassword());
            return user;
        });

        authService.register(userRequest);

        verify(userRepository).save(argThat(savedUser -> !savedUser.getPassword().equals("password123")));
    }

    @Test
    void login_ShouldWorkWithDifferentUsernames() {
        loginRequest.setUsername("anotheruser");
        user.setUsername("anotheruser");

        when(userRepository.findByUsername("anotheruser")).thenReturn(Optional.of(user));
        String encodedPassword = PasswordEncoder.encode("password123");
        user.setPassword(encodedPassword);

        LoginResponse response = authService.login(loginRequest);

        assertNotNull(response);
        assertEquals("anotheruser", response.getUsername());
        verify(userRepository).findByUsername("anotheruser");
    }

    @Test
    void register_ShouldWorkWithDifferentEmails() {
        userRequest.setEmail("another@example.com");
        user.setEmail("another@example.com");

        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        LoginResponse response = authService.register(userRequest);

        assertNotNull(response);
        assertEquals("another@example.com", response.getEmail());
    }
}
