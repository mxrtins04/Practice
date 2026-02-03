package com.mxr.mfds.repository;

import com.mxr.mfds.entity.Tank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TankRepository extends JpaRepository<Tank, Long> {

    List<Tank> findByFuelId(Long fuelId);

    Optional<Tank> findFirstByFuelId(Long fuelId);

    List<Tank> findByCurrentQuantityLessThan(Double threshold);

    Optional<Tank> findByFuelName(String fuelName);
}
