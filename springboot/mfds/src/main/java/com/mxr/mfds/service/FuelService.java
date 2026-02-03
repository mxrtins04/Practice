package com.mxr.mfds.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mxr.mfds.entity.Fuel;
import com.mxr.mfds.repository.FuelRepository;
@Service
public class FuelService {

    @Autowired
    private FuelRepository fuelRepository;

    public Fuel createFuel(String name, BigDecimal pricePerLiter) {
        if (fuelRepository.existsByName(name)) {
        throw new IllegalArgumentException("Fuel with name '" + name + "' already exists");
    }
        Fuel newFuel = new Fuel(null, name, pricePerLiter);
        fuelRepository.save(newFuel);
        return newFuel;

    }
}
