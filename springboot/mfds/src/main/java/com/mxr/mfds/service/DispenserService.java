package com.mxr.mfds.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mxr.mfds.entity.Dispenser;
import com.mxr.mfds.entity.Tank;
import com.mxr.mfds.repository.DispenserRepository;
import com.mxr.mfds.repository.TankRepository;
import com.mxr.mfds.service.TankService;

@Service
public class DispenserService {

    @Autowired
    private DispenserRepository dispenserRepository;

    @Autowired
    private TankRepository tankRepository;

    @Autowired
    private TankService tankService;

    public Dispenser getDispenserById(Long id) {
        return dispenserRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Dispenser not found with id: " + id));
    }

    public List<Dispenser> getAllDispensers() {
        return dispenserRepository.findAll();
    }

    public Tank dispenseFuel(String fuelName, Double litersToDispense) {
        validateFuelRequest(fuelName, litersToDispense);

        Tank tank = tankRepository.findByFuelName(fuelName)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Fuel '" + fuelName + "' not available. Please check available fuel types."));

        return tankService.dispenseFuel(tank.getId(), litersToDispense);
    }

    private void validateFuelRequest(String fuelName, Double litersToDispense) {
        if (fuelName == null || fuelName.trim().isEmpty()) {
            throw new IllegalArgumentException("Fuel name cannot be empty");
        }

        if (litersToDispense == null) {
            throw new IllegalArgumentException("Liters to dispense cannot be null");
        }

        if (litersToDispense <= 0) {
            throw new IllegalArgumentException("Liters to dispense must be positive. Requested: " + litersToDispense);
        }
    }

    public List<Tank> getAllTanksForDispenser(Long dispenserId) {
        getDispenserById(dispenserId);
        return tankRepository.findAll();
    }
}
