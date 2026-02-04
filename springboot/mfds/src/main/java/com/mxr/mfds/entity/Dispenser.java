package com.mxr.mfds.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "dispensers")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Dispenser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "current_attendant_id")
    private FuelAttendant currentAttendant;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "dispenser_tanks", joinColumns = @JoinColumn(name = "dispenser_id"), inverseJoinColumns = @JoinColumn(name = "tank_id"))
    private List<Tank> tanks;

    public void assignAttendant(FuelAttendant attendant) {
        this.currentAttendant = attendant;
    }

    public void removeAttendant() {
        this.currentAttendant = null;
    }

    public boolean isAttendantAssigned() {
        return this.currentAttendant != null;
    }

    public Tank getTankByFuelName(String fuelName) {
        return tanks.stream()
                .filter(tank -> tank.getFuel().getName().equals(fuelName))
                .findFirst()
                .orElse(null);
    }
}
