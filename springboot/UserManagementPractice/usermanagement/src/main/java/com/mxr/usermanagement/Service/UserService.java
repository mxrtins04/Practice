package com.mxr.usermanagement.Service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.mxr.usermanagement.data.dto.requests.CreateUserDTO;
import com.mxr.usermanagement.data.dto.requests.Response;
import com.mxr.usermanagement.model.User;
import com.mxr.usermanagement.data.repo.UserRepo;
import com.mxr.usermanagement.exceptions.UserNotFoundException;



@Service
public class UserService {


    private UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
    

    public Response createUser(CreateUserDTO validRequestDTO) {
        User user = new User();
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

    public Response getUserById(long number) {
        User user = userRepo.getUserById(number);
        if(user == null){
            throw new UserNotFoundException(number);
        }   

        
        return mapToResponseDTO(user);
    }

    public void updateUserDetailUser(Long id, CreateUserDTO userRequestDTO) {
        User user = userRepo.getUserById(id);
        if(user == null){
            throw new UserNotFoundException(id);
        }
        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());
        user.setUserName(userRequestDTO.getUsername());
        user.setUpdatedAt(LocalDateTime.now());
        
        userRepo.saveUser(user);
        
    }
}

