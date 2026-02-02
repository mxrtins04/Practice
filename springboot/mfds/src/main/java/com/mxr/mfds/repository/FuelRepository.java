package com.mxr.mfds.repository;

import com.mxr.mfds.entity.Fuel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FuelRepository extends JpaRepository<Fuel, Long> {
    
    Optional<Fuel> findByName(String name);
    
    boolean existsByName(String name);
}
