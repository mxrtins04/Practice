package com.mxr.mfds.repository;

import com.mxr.mfds.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    List<Transaction> findByFuelAttendantId(Long fuelAttendantId);
    
    List<Transaction> findByFuelAttendantIdAndTransactionTimeBetween(
        Long fuelAttendantId, LocalDateTime startTime, LocalDateTime endTime);
    
    BigDecimal sumAmountByFuelAttendantId(Long fuelAttendantId);
    
    List<Transaction> findByDispenserId(Long dispenserId);
    
    List<Transaction> findByFuelId(Long fuelId);
}
