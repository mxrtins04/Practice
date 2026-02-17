package com.mxr.usermanagement.Service;

import java.time.LocalDateTime;
import java.util.function.BooleanSupplier;

import com.mxr.usermanagement.data.dto.requests.CreateUserDTO;
import com.mxr.usermanagement.data.dto.requests.Response;
import com.mxr.usermanagement.model.User;
import com.mxr.usermanagement.data.repo.UserRepo;
import com.mxr.usermanagement.exceptions.UserNotFoundException;
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

    public User getUserById(long number) {
        if(userRepo.getUserById(number) == null){
            throw new UserNotFoundException(number);
        }   
        return userRepo.getUserById(number);
    }

    public void updateUserDetailUser(Long id, CreateUserDTO userRequestDTO) {
        if(userRepo.getUserById(id) == null){
            throw new UserNotFoundException(id);
        }
        User user = userRepo.findById(id).get();
        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());
        user.setUserName(userRequestDTO.getUsername());
        user.setUpdatedAt(LocalDateTime.now());
        
        userRepo.saveUser(user);
        
    }
}

