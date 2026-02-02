package com.mxr.mfds.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tanks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Tank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "capacity", nullable = false)
    private Double capacity;

    @Column(name = "current_quantity", nullable = false)
    private Double currentQuantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fuel_id", nullable = false)
    private Fuel fuel;
}
