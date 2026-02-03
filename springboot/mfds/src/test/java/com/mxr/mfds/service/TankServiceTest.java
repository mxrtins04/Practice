package com.mxr.mfds.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mxr.mfds.entity.Tank;
import com.mxr.mfds.entity.Fuel;
import com.mxr.mfds.repository.TankRepository;

@ExtendWith(MockitoExtension.class)
public class TankServiceTest {

    @Mock
    private TankRepository tankRepository;

    @InjectMocks
    private TankService tankService;

    @Test
    void shouldCreateTank_WhenNewFuelIsCreated() {
        Fuel fuel = new Fuel(1L, "Petrol", new BigDecimal("150"));
        Tank expectedTank = new Tank(1L, 10000.0, 0.0, fuel);

        when(tankRepository.findByFuelName("Petrol")).thenReturn(Optional.of(expectedTank));

        Tank result = tankService.getTankByFuelName("Petrol");

        assertEquals(1L, result.getId());
        assertEquals(10000.0, result.getCapacity());
        assertEquals(0.0, result.getCurrentQuantity());
        assertEquals("Petrol", result.getFuel().getName());
        verify(tankRepository).findByFuelName("Petrol");
    }

    @Test
    void shouldReturnTank_WhenValidIdProvided() {
        Fuel fuel = new Fuel(1L, "Petrol", new BigDecimal("1.50"));
        Tank expectedTank = new Tank(1L, 1000.0, 500.0, fuel);

        when(tankRepository.findById(1L)).thenReturn(Optional.of(expectedTank));

        Tank result = tankService.getTankById(1L);

        assertEquals(1L, result.getId());
        assertEquals(1000.0, result.getCapacity());
        assertEquals(500.0, result.getCurrentQuantity());
        assertEquals("Petrol", result.getFuel().getName());
        verify(tankRepository).findById(1L);
    }

    @Test
    void shouldThrowException_WhenTankNotFoundById() {
        when(tankRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> tankService.getTankById(999L));
        verify(tankRepository).findById(999L);
    }

    @Test
    void shouldThrowException_WhenTankNotFoundByFuelName() {
        when(tankRepository.findByFuelName("NonExistent")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> tankService.getTankByFuelName("NonExistent"));
        verify(tankRepository).findByFuelName("NonExistent");
    }

    @Test
    void shouldReturnTanksWithLowQuantity_WhenThresholdProvided() {
        Fuel fuel = new Fuel(1L, "Petrol", new BigDecimal("1.50"));
        List<Tank> expectedTanks = List.of(
                new Tank(1L, 10000.0, 100.0, fuel),
                new Tank(2L, 20000.0, 150.0, fuel));

        when(tankRepository.findByCurrentQuantityLessThan(200.0)).thenReturn(expectedTanks);

        List<Tank> result = tankService.getTanksWithLowQuantity(200.0);

        assertEquals(2, result.size());
        assertEquals(100.0, result.get(0).getCurrentQuantity());
        assertEquals(150.0, result.get(1).getCurrentQuantity());
        verify(tankRepository).findByCurrentQuantityLessThan(200.0);
    }

    @Test
    void shouldReturnCurrentFuelQuantity_WhenValidTankId() {
        Fuel fuel = new Fuel(1L, "Petrol", new BigDecimal("150"));
        Tank tank = new Tank(1L, 10000.0, 750.0, fuel);

        when(tankRepository.findById(1L)).thenReturn(Optional.of(tank));

        Double result = tankService.getCurrentFuelQuantity(1L);

        assertEquals(750.0, result);
        verify(tankRepository).findById(1L);
    }

    @Test
    void shouldUpdateFuelQuantity_WhenValidQuantityProvided() {
        Fuel fuel = new Fuel(1L, "Petrol", new BigDecimal("1.50"));
        Tank existingTank = new Tank(1L, 10000.0, 500.0, fuel);
        Tank updatedTank = new Tank(1L, 10000.0, 800.0, fuel);

        when(tankRepository.findById(1L)).thenReturn(Optional.of(existingTank));
        when(tankRepository.save(any(Tank.class))).thenReturn(updatedTank);

        Tank result = tankService.updateFuelQuantity(1L, 800.0);

        assertEquals(800.0, result.getCurrentQuantity());
        verify(tankRepository).findById(1L);
        verify(tankRepository).save(any(Tank.class));
    }

    @Test
    void shouldThrowException_WhenUpdatingWithNegativeQuantity() {
        Fuel fuel = new Fuel(1L, "Petrol", new BigDecimal("1.50"));
        Tank tank = new Tank(1L, 10000.0, 500.0, fuel);

        when(tankRepository.findById(1L)).thenReturn(Optional.of(tank));

        assertThrows(IllegalArgumentException.class, () -> tankService.updateFuelQuantity(1L, -100.0));
        verify(tankRepository).findById(1L);
    }

    @Test
    void shouldThrowException_WhenUpdatingWithQuantityExceedingCapacity() {
        Fuel fuel = new Fuel(1L, "Petrol", new BigDecimal("1.50"));
        Tank tank = new Tank(1L, 10000.0, 500.0, fuel);

        when(tankRepository.findById(1L)).thenReturn(Optional.of(tank));

        assertThrows(IllegalArgumentException.class, () -> tankService.updateFuelQuantity(1L, 15000.0));
        verify(tankRepository).findById(1L);
    }

    @Test
    void shouldReturnAvailableCapacity_WhenValidTankId() {
        Fuel fuel = new Fuel(1L, "Petrol", new BigDecimal("1.50"));
        Tank tank = new Tank(1L, 10000.0, 3000.0, fuel);

        when(tankRepository.findById(1L)).thenReturn(Optional.of(tank));

        Double result = tankService.getAvailableCapacity(1L);

        assertEquals(7000.0, result);
        verify(tankRepository).findById(1L);
    }

    @Test
    void shouldDispenseFuel_WhenSufficientFuelAvailable() {
        Fuel fuel = new Fuel(1L, "Petrol", new BigDecimal("1.50"));
        Tank tank = new Tank(1L, 10000.0, 5000.0, fuel);
        Tank updatedTank = new Tank(1L, 10000.0, 4500.0, fuel);

        when(tankRepository.findById(1L)).thenReturn(Optional.of(tank));
        when(tankRepository.save(any(Tank.class))).thenReturn(updatedTank);

        Tank result = tankService.dispenseFuel(1L, 500.0);

        assertEquals(4500.0, result.getCurrentQuantity());
        verify(tankRepository).findById(1L);
        verify(tankRepository).save(any(Tank.class));
    }

    @Test
    void shouldIncreaseAvailableCapacity_WhenFuelIsDispensed() {
        Fuel fuel = new Fuel(1L, "Petrol", new BigDecimal("1.50"));
        Tank tank = new Tank(1L, 10000.0, 5000.0, fuel);
        Tank updatedTank = new Tank(1L, 10000.0, 3000.0, fuel);

        when(tankRepository.findById(1L)).thenReturn(Optional.of(tank), Optional.of(updatedTank));
        when(tankRepository.save(any(Tank.class))).thenReturn(updatedTank);

        Double initialCapacity = tankService.getAvailableCapacity(1L);
        Tank result = tankService.dispenseFuel(1L, 2000.0);
        Double finalCapacity = tankService.getAvailableCapacity(1L);

        assertEquals(5000.0, initialCapacity);
        assertEquals(7000.0, finalCapacity);
        assertEquals(3000.0, result.getCurrentQuantity());
        verify(tankRepository, org.mockito.Mockito.times(3)).findById(1L);
        verify(tankRepository).save(any(Tank.class));
    }

    @Test
    void shouldThrowException_WhenDispensingMoreThanAvailable() {
        Fuel fuel = new Fuel(1L, "Petrol", new BigDecimal("1.50"));
        Tank tank = new Tank(1L, 10000.0, 1000.0, fuel);

        when(tankRepository.findById(1L)).thenReturn(Optional.of(tank));

        assertThrows(IllegalArgumentException.class, () -> tankService.dispenseFuel(1L, 1500.0));
        verify(tankRepository).findById(1L);
    }

    @Test
    void shouldRefillTank_WhenWithinCapacity() {
        Fuel fuel = new Fuel(1L, "Petrol", new BigDecimal("1.50"));
        Tank tank = new Tank(1L, 10000.0, 3000.0, fuel);
        Tank updatedTank = new Tank(1L, 10000.0, 5000.0, fuel);

        when(tankRepository.findById(1L)).thenReturn(Optional.of(tank));
        when(tankRepository.save(any(Tank.class))).thenReturn(updatedTank);

        Tank result = tankService.refillTank(1L, 2000.0);

        assertEquals(5000.0, result.getCurrentQuantity());
        verify(tankRepository).findById(1L);
        verify(tankRepository).save(any(Tank.class));
    }

    @Test
    void shouldThrowException_WhenRefillingExceedsCapacity() {
        Fuel fuel = new Fuel(1L, "Petrol", new BigDecimal("1.50"));
        Tank tank = new Tank(1L, 10000.0, 8000.0, fuel);

        when(tankRepository.findById(1L)).thenReturn(Optional.of(tank));

        assertThrows(IllegalArgumentException.class, () -> tankService.refillTank(1L, 3000.0));
        verify(tankRepository).findById(1L);
    }

    @Test
    void shouldFillToCapacity_WhenCalled() {
        Fuel fuel = new Fuel(1L, "Petrol", new BigDecimal("1.50"));
        Tank tank = new Tank(1L, 10000.0, 3000.0, fuel);
        Tank updatedTank = new Tank(1L, 10000.0, 10000.0, fuel);

        when(tankRepository.findById(1L)).thenReturn(Optional.of(tank));
        when(tankRepository.save(any(Tank.class))).thenReturn(updatedTank);

        Tank result = tankService.fillToCapacity(1L);

        assertEquals(10000.0, result.getCurrentQuantity());
        verify(tankRepository).findById(1L);
        verify(tankRepository).save(any(Tank.class));
    }

    @Test
    void shouldGetAllTanks_WhenRequested() {
        Fuel fuel1 = new Fuel(1L, "Petrol", new BigDecimal("1.50"));
        Fuel fuel2 = new Fuel(2L, "Diesel", new BigDecimal("1.80"));
        List<Tank> expectedTanks = List.of(
                new Tank(1L, 10000.0, 5000.0, fuel1),
                new Tank(2L, 15000.0, 7500.0, fuel2));

        when(tankRepository.findAll()).thenReturn(expectedTanks);

        List<Tank> result = tankService.getAllTanks();

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
        verify(tankRepository).findAll();
    }
}
