package com.mxr.usermanagement.data.repo;

import com.mxr.usermanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface userRepo extends JpaRepository<User, Long> {
    
}
