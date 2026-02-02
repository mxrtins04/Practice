package com.mxr.mfds.repository;

import com.mxr.mfds.entity.FuelAttendant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FuelAttendantRepository extends JpaRepository<FuelAttendant, Long> {
    
    Optional<FuelAttendant> findByName(String name);
    
    boolean existsByName(String name);
}
