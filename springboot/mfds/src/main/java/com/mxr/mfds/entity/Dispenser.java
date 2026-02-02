package com.mxr.mfds.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "dispensers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Dispenser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "dispenser_tanks", joinColumns = @JoinColumn(name = "dispenser_id"), inverseJoinColumns = @JoinColumn(name = "tank_id"))
    private List<Tank> tanks;

    public Tank getTankByFuelName(String fuelName) {
        return tanks.stream()
                .filter(tank -> tank.getFuel().getName().equals(fuelName))
                .findFirst()
                .orElse(null);
    }
}
