package com.mxr.usermanagement;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.mxr.usermanagement.Service.UserService;
import com.mxr.usermanagement.controller.UserController;
import com.mxr.usermanagement.data.dto.requests.CreateUserDTO;
import com.mxr.usermanagement.data.dto.requests.Response;
import com.mxr.usermanagement.exceptions.GlobalExceptionHandler;

import com.fasterxml.jackson.databind.json.JsonMapper;

@ExtendWith(MockitoExtension.class)
public class ControllerTest {
    private MockMvc mockMvc;

    @Mock
    UserService userService;

    @InjectMocks
    private UserController userController;

    private ObjectMapper objectMapper;
    
    private CreateUserDTO request;
    private Response response;


    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
        .setControllerAdvice(new GlobalExceptionHandler())
        .build();

        objectMapper = JsonMapper.builder()
                        .findAndAddModules()
                        .build();

        request = new CreateUserDTO(
            "Martins",
            "martins@example.com",
            "mxr"
        );

        response = new Response(
            1L,
            "Martins",
            "cool",
            "martins@example.com",
            "mxr",
            LocalDateTime.now(),
            LocalDateTime.now()
        );
    }

    

}
