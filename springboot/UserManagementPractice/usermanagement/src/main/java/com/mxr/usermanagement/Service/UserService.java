
/*
Statelesness in this service means that no data is stored or persisted during or after any of these operations. Right now all data required to perform any operation is passed as a parameter(request).  
For this to be scaled horizontally, the data should be stored in a database or a distributed cache.
If session data lived in a local field, it would become stateful i.e Each time the service a new instance of the class is called and the 
data that existed in the previous instance is lost. Not just that, other instances dont know what happens in other instances, leading to 
unpredictable behavior.
*/
package com.mxr.usermanagement.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mxr.usermanagement.config.UserServiceProperties;
import com.mxr.usermanagement.data.dto.requests.CreateUserDTO;
import com.mxr.usermanagement.data.dto.requests.Response;
import com.mxr.usermanagement.model.User;
import com.mxr.usermanagement.data.repo.UserRepo;
import com.mxr.usermanagement.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




@Service
public class UserService {


    private final UserRepo userRepo;
    private final UserServiceProperties userServiceProperties;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepo userRepo, UserServiceProperties userServiceProperties) {
        this.userRepo = userRepo;
        this.userServiceProperties = userServiceProperties;
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

    public List<Response> getAllUsers(){
        List<User> users = userRepo.getAllUsers();
        logger.info("Fetching users. Max users per page is {}", userServiceProperties.getMaxUsersPerPage());
        return users.stream().limit(userServiceProperties.getMaxUsersPerPage()).map(this::mapToResponseDTO).toList();
    }

    private Response mapToResponseDTO(User user){
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

        logger.info("User found with id: {}", number);
        return mapToResponseDTO(user);
    }

    public Response updateUserDetailUser(Long id, CreateUserDTO userRequestDTO) {
        User user = userRepo.getUserById(id);
        if(user == null){
            throw new UserNotFoundException(id);
        }
        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());
        user.setUserName(userRequestDTO.getUsername());
        user.setUpdatedAt(LocalDateTime.now());
        
        userRepo.saveUser(user);
        logger.info("User updated with id: {}", id);
        return mapToResponseDTO(user);
        
    }


    public void deleteUser(Long id) {
        User user = userRepo.getById(id);
        if(user == null){
            throw new UserNotFoundException(id);
        }
        userRepo.deleteUserById(id);
        logger.info("User deleted with id: {}", id);
    }
}




