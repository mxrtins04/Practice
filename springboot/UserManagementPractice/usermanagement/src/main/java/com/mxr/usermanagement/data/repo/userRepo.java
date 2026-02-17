package com.mxr.usermanagement.data.repo;

import com.mxr.usermanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface userRepo extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    User getUserById(Long id);

    User getUserByUsername(String username);
    List<User> getUsersByName(String name);
}
