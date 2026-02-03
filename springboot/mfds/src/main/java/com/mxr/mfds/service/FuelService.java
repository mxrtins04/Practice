package com.mxr.mfds.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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

    public Fuel getFuelById(Long id) {
        return fuelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fuel not found with id: " + id));
    }

    public Fuel getFuelByName(String name) {
        return fuelRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Fuel not found with name: " + name));
    }

    public List<Fuel> getAllFuels() {
        return fuelRepository.findAll();
    }

    public Fuel updateFuel(Long id, Fuel fuelDetails) {
        return fuelRepository.findById(id)
                .map(fuel -> {
                    fuel.setName(fuelDetails.getName());
                    fuel.setPricePerLiter(fuelDetails.getPricePerLiter());
                    return fuelRepository.save(fuel);
                })
                .orElseThrow(() -> new IllegalArgumentException("Fuel not found with id: " + id));
    }

    public Fuel updateFuelPrice(Long id, BigDecimal newPrice) {
        return fuelRepository.findById(id)
                .map(fuel -> {
                    fuel.setPricePerLiter(newPrice);
                    return fuelRepository.save(fuel);
                })
                .orElseThrow(() -> new IllegalArgumentException("Fuel not found with id: " + id));
    }

    public void deleteFuel(Long id) {
        if (!fuelRepository.existsById(id)) {
            throw new IllegalArgumentException("Fuel not found with id: " + id);
        }
        fuelRepository.deleteById(id);
    }
}
