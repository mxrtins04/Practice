package com.mxr.mfds.repository;

import com.mxr.mfds.entity.Dispenser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DispenserRepository extends JpaRepository<Dispenser, Long> {
    
    Optional<Dispenser> findByName(String name);
    
    boolean existsByName(String name);
}
