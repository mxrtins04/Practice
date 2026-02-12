package com.mxr.usermanagement;


import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.mxr.usermanagement.Service.UserService;
import com.mxr.usermanagement.data.repo.userRepo;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    
    @Mock
    private userRepo userRepo;

    @InjectMocks
    private UserService userService;


    @Test
    void when() {

        assertTrue(userRepo.findAll().isEmpty());
    }

}

