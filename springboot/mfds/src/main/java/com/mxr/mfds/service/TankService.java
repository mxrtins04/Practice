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
}
