package com.mxr.mfds.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mxr.mfds.entity.Tank;
import com.mxr.mfds.repository.TankRepository;

@Service
public class TankService {

    @Autowired
    private TankRepository tankRepository;

    public Tank getTankById(Long id) {
        return tankRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tank not found with id: " + id));
    }

    public Tank getTankByFuelName(String fuel) {
        return tankRepository.findByFuelName(fuel)
                .orElseThrow(() -> new IllegalArgumentException("Tank not found with fuel name: " + fuel));
    }

    public List<Tank> getTanksWithLowQuantity(Double threshold) {
        return tankRepository.findByCurrentQuantityLessThan(threshold);
    }

    public Double getCurrentFuelQuantity(Long tankId) {
        Tank tank = getTankById(tankId);
        return tank.getCurrentQuantity();
    }

    public Tank updateFuelQuantity(Long tankId, Double newQuantity) {
        Tank tank = getTankById(tankId);

        if (newQuantity < 0) {
            throw new IllegalArgumentException("Fuel quantity cannot be negative");
        }

        if (newQuantity > tank.getCapacity()) {
            throw new IllegalArgumentException("Fuel quantity cannot exceed tank capacity of " + tank.getCapacity());
        }

        tank.setCurrentQuantity(newQuantity);
        return tankRepository.save(tank);
    }

    public Double getAvailableCapacity(Long tankId) {
        Tank tank = getTankById(tankId);
        return tank.getCapacity() - tank.getCurrentQuantity();
    }

    public Tank dispenseFuel(Long tankId, Double litersToDispense) {
        Tank tank = getTankById(tankId);

        if (litersToDispense <= 0) {
            throw new IllegalArgumentException("Liters to dispense must be positive");
        }

        if (tank.getCurrentQuantity() < litersToDispense) {
            throw new IllegalArgumentException("Insufficient fuel in tank. Available: " + tank.getCurrentQuantity()
                    + "Liters, Requested: " + litersToDispense + "Liters");
        }

        Double newQuantity = tank.getCurrentQuantity() - litersToDispense;
        tank.setCurrentQuantity(newQuantity);
        return tankRepository.save(tank);
    }

    public Tank refillTank(Long tankId, Double litersToAdd) {
        Tank tank = getTankById(tankId);

        if (litersToAdd <= 0) {
            throw new IllegalArgumentException("Liters to add must be positive");
        }

        Double newQuantity = tank.getCurrentQuantity() + litersToAdd;

        if (newQuantity > tank.getCapacity()) {
            throw new IllegalArgumentException("Cannot add " + litersToAdd + "L. Tank capacity: " + tank.getCapacity()
                    + "L, Current: " + tank.getCurrentQuantity() + "L, Available space: "
                    + (tank.getCapacity() - tank.getCurrentQuantity()) + "L");
        }

        tank.setCurrentQuantity(newQuantity);
        return tankRepository.save(tank);
    }

    public Tank fillToCapacity(Long tankId) {
        Tank tank = getTankById(tankId);
        tank.setCurrentQuantity(tank.getCapacity());
        return tankRepository.save(tank);
    }

    public List<Tank> getAllTanks() {
        return tankRepository.findAll();
    }
}
