package com.mxr.mfds.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "fuel_attendants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FuelAttendant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "fuelAttendant", fetch = FetchType.EAGER)
    private List<Transaction> transactions;
}
