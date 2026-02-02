package com.mxr.mfds.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Table(name = "fuels")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Fuel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price_per_liter", nullable = false)
    private BigDecimal pricePerLiter;
}
