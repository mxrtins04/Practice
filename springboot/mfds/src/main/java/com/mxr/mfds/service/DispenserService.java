package com.mxr.mfds.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mxr.mfds.entity.Dispenser;
import com.mxr.mfds.entity.FuelAttendant;
import com.mxr.mfds.entity.Tank;
import com.mxr.mfds.repository.DispenserRepository;
import com.mxr.mfds.repository.TankRepository;
import com.mxr.mfds.service.TankService;
import com.mxr.mfds.service.FuelAttendantService;

@Service
public class DispenserService {

    @Autowired
    private DispenserRepository dispenserRepository;

    @Autowired
    private TankRepository tankRepository;

    @Autowired
    private TankService tankService;

    @Autowired
    private FuelAttendantService fuelAttendantService;

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

    public Dispenser assignAttendantToDispenser(Long dispenserId, Long attendantId) {
        Dispenser dispenser = getDispenserById(dispenserId);
        FuelAttendant attendant = fuelAttendantService.getAttendantById(attendantId);

        if (dispenser.isAttendantAssigned()) {
            throw new IllegalArgumentException("Dispenser already has an attendant assigned: " + dispenserId);
        }

        dispenser.assignAttendant(attendant);
        return dispenserRepository.save(dispenser);
    }

    public Dispenser removeAttendantFromDispenser(Long dispenserId) {
        Dispenser dispenser = getDispenserById(dispenserId);
        dispenser.removeAttendant();
        return dispenserRepository.save(dispenser);
    }

    public FuelAttendant getCurrentAttendantForDispenser(Long dispenserId) {
        Dispenser dispenser = getDispenserById(dispenserId);

        if (!dispenser.isAttendantAssigned()) {
            throw new IllegalArgumentException("No attendant assigned to dispenser: " + dispenserId);
        }

        return dispenser.getCurrentAttendant();
    }
    }
}
