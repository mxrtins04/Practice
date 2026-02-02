package com.mxr.mfds.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "liters", nullable = false)
    private Double liters;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "price_per_liter_at_time", nullable = false)
    private BigDecimal pricePerLiterAtTime;

    @Column(name = "transaction_time", nullable = false)
    private LocalDateTime transactionTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fuel_id", nullable = false)
    private Fuel fuel;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fuel_attendant_id", nullable = false)
    private FuelAttendant fuelAttendant;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dispenser_id", nullable = false)
    private Dispenser dispenser;

    @PrePersist
    protected void onCreate() {
        transactionTime = LocalDateTime.now();
    }
}
