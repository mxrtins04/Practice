package com.mxr.mfds.repository;

import com.mxr.mfds.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    List<Transaction> findByFuelAttendantId(Long fuelAttendantId);
    
    List<Transaction> findByFuelAttendantIdAndTransactionTimeBetween(
        Long fuelAttendantId, LocalDateTime startTime, LocalDateTime endTime);
    
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.fuelAttendant.id = :attendantId")
    BigDecimal sumAmountByFuelAttendantId(@Param("attendantId") Long attendantId);
    
    List<Transaction> findByDispenserId(Long dispenserId);
    
    List<Transaction> findByFuelName(String fuelName);
}
