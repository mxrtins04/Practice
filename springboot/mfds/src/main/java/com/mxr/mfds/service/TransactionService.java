package com.mxr.mfds.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mxr.mfds.entity.Dispenser;
import com.mxr.mfds.entity.Fuel;
import com.mxr.mfds.entity.FuelAttendant;
import com.mxr.mfds.entity.Transaction;
import com.mxr.mfds.repository.TransactionRepository;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private DispenserService dispenserService;

    @Autowired
    private FuelService fuelService;

    public Transaction createTransaction(Double liters, BigDecimal amount, BigDecimal pricePerLiterAtTime,
            Fuel fuel, FuelAttendant fuelAttendant, Dispenser dispenser) {
        validateTransactionData(liters, amount, pricePerLiterAtTime, fuel, fuelAttendant, dispenser);

        Transaction transaction = new Transaction();
        transaction.setLiters(liters);
        transaction.setAmount(amount);
        transaction.setPricePerLiterAtTime(pricePerLiterAtTime);
        transaction.setFuel(fuel);
        transaction.setFuelAttendant(fuelAttendant);
        transaction.setDispenser(dispenser);

        return transactionRepository.save(transaction);
    }

    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found with id: " + id));
    }

    public Transaction processSale(Long dispenserId, Long fuelId, Double liters) {
        Dispenser dispenser = dispenserService.getDispenserById(dispenserId);

        if (!dispenser.isAttendantAssigned()) {
            throw new IllegalArgumentException("No attendant assigned to dispenser: " + dispenserId);
        }

        FuelAttendant attendant = dispenser.getCurrentAttendant();
        Fuel fuel = fuelService.getFuelById(fuelId);

        if (liters == null || liters <= 0) {
            throw new IllegalArgumentException("Liters must be a positive number");
        }

        dispenserService.dispenseFuel(fuel.getName(), liters);

        BigDecimal amount = fuel.getPricePerLiter().multiply(BigDecimal.valueOf(liters));

        return createTransaction(liters, amount, fuel.getPricePerLiter(), fuel, attendant, dispenser);
    }

    public List<Transaction> getTransactionsByAttendant(Long fuelAttendantId) {
        return transactionRepository.findByFuelAttendantId(fuelAttendantId);
    }

    public List<Transaction> getTransactionsByDispenser(Long dispenserId) {
        return transactionRepository.findByDispenserId(dispenserId);
    }

    public List<Transaction> getTransactionsByFuel(Long fuelId) {
        return transactionRepository.findByFuelId(fuelId);
    }

    public List<Transaction> getTransactionsByDateRange(LocalDateTime startTime, LocalDateTime endTime) {
        return List.of();
    }

    public BigDecimal calculateTotalSales() {
        return BigDecimal.ZERO;
    }

    public BigDecimal calculateSalesByAttendant(Long attendantId) {
        return transactionRepository.sumAmountByFuelAttendantId(attendantId);
    }

    private void validateTransactionData(Double liters, BigDecimal amount, BigDecimal pricePerLiterAtTime,
            Fuel fuel, FuelAttendant fuelAttendant, Dispenser dispenser) {
        if (liters == null || liters <= 0) {
            throw new IllegalArgumentException("Liters must be a positive number");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be a positive number");
        }

        if (pricePerLiterAtTime == null || pricePerLiterAtTime.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price per liter must be a positive number");
        }

        if (fuel == null) {
            throw new IllegalArgumentException("Fuel cannot be null");
        }

        if (fuelAttendant == null) {
            throw new IllegalArgumentException("Fuel attendant cannot be null");
        }

        if (dispenser == null) {
            throw new IllegalArgumentException("Dispenser cannot be null");
        }
    }
}
