package com.mxr.usermanagement.Service;

import java.time.LocalDateTime;
import java.util.function.BooleanSupplier;

import com.mxr.usermanagement.data.dto.requests.CreateUserDTO;
import com.mxr.usermanagement.data.dto.requests.Response;
import com.mxr.usermanagement.model.User;
import com.mxr.usermanagement.data.repo.UserRepo;
public class UserService {
    UserRepo userRepo;
    

    public Response createUser(CreateUserDTO validRequestDTO) {
        User user = new User();
        user.setId(user.getId());       
        user.setName(validRequestDTO.getName());
        user.setEmail(validRequestDTO.getEmail());
        user.setUserName(validRequestDTO.getUsername());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        
        userRepo.saveUser(user);
        Response response = mapToResponseDTO(user);
        return response;
    }

    public Response mapToResponseDTO(User user){
        Response response  = Response.builder()
        .name(user.getName())
        .email(user.getEmail())
        .username(user.getUserName())
        .createdAt(user.getCreatedAt())
        .updatedAt(user.getUpdatedAt())
        .build();
        return response;
    }
}

