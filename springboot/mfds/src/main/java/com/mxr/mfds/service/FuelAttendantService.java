package com.mxr.mfds.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mxr.mfds.entity.FuelAttendant;
import com.mxr.mfds.entity.Transaction;
import com.mxr.mfds.repository.FuelAttendantRepository;

@Service
public class FuelAttendantService {
    
    @Autowired
    private FuelAttendantRepository fuelAttendantRepository;
    
    public FuelAttendant getAttendantById(Long id) {
        return fuelAttendantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fuel attendant not found with id: " + id));
    }
    
    public FuelAttendant getAttendantByName(String name) {
        return fuelAttendantRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Fuel attendant not found with name: " + name));
    }
    
    public List<FuelAttendant> getAllAttendants() {
        return fuelAttendantRepository.findAll();
    }
    
    public FuelAttendant createAttendant(FuelAttendant attendant) {
        validateAttendant(attendant);
        
        if (fuelAttendantRepository.existsByName(attendant.getName())) {
            throw new IllegalArgumentException("Fuel attendant already exists with name: " + attendant.getName());
        }
        
        return fuelAttendantRepository.save(attendant);
    }
    
    public List<Transaction> getAttendantTransactions(Long attendantId) {
        FuelAttendant attendant = getAttendantById(attendantId);
        return attendant.getTransactions();
    }
    
    private void validateAttendant(FuelAttendant attendant) {
        if (attendant.getName() == null || attendant.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Fuel attendant name cannot be null or empty");
        }
    }
}
