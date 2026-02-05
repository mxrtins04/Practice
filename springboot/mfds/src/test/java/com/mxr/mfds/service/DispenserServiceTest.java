package com.mxr.mfds.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

import com.mxr.mfds.entity.Dispenser;
import com.mxr.mfds.entity.Fuel;
import com.mxr.mfds.entity.Tank;
import com.mxr.mfds.repository.DispenserRepository;
import com.mxr.mfds.repository.TankRepository;
import com.mxr.mfds.service.TankService;

@ExtendWith(MockitoExtension.class)
public class DispenserServiceTest {

    @Mock
    private DispenserRepository dispenserRepository;

    @Mock
    private TankRepository tankRepository;

    @Mock
    private TankService tankService;

    @InjectMocks
    private DispenserService dispenserService;

    @Test
    void shouldHaveAccessToAllTanks_WhenDispenserIsInstantiated() {
        Fuel petrol = new Fuel(1L, "Petrol", new BigDecimal("1.50"));
        Fuel diesel = new Fuel(2L, "Diesel", new BigDecimal("1.80"));
        List<Tank> allTanks = List.of(
                new Tank(1L, 10000.0, 5000.0, petrol),
                new Tank(2L, 15000.0, 7500.0, diesel));
        Dispenser dispenser = new Dispenser();

        when(dispenserRepository.findById(1L)).thenReturn(Optional.of(dispenser));
        when(tankRepository.findAll()).thenReturn(allTanks);

        List<Tank> availableTanks = dispenserService.getAllTanksForDispenser(1L);

        assertEquals(2, availableTanks.size());
        assertEquals("Petrol", availableTanks.get(0).getFuel().getName());
        assertEquals("Diesel", availableTanks.get(1).getFuel().getName());
        verify(dispenserRepository).findById(1L);
        verify(tankRepository).findAll();
    }

    @Test
    void shouldThrowException_WhenDispenserNotFoundById() {
        when(dispenserRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> dispenserService.getDispenserById(999L));
        verify(dispenserRepository).findById(999L);
    }

    @Test
    void shouldReturnAllDispensers_WhenRequested() {
        List<Dispenser> expectedDispensers = List.of(
                new Dispenser(),
                new Dispenser());

        when(dispenserRepository.findAll()).thenReturn(expectedDispensers);

        List<Dispenser> result = dispenserService.getAllDispensers();

        assertEquals(2, result.size());
        verify(dispenserRepository).findAll();
    }

    @Test
    void shouldDispenseFuel_WhenValidFuelProvided() {
        Fuel fuel = new Fuel(1L, "Petrol", new BigDecimal("1.50"));
        Tank tank = new Tank(1L, 10000.0, 5000.0, fuel);
        Tank updatedTank = new Tank(1L, 10000.0, 4500.0, fuel);

        when(tankRepository.findByFuelName("Petrol")).thenReturn(Optional.of(tank));
        when(tankService.dispenseFuel(1L, 500.0)).thenReturn(updatedTank);

        Tank result = dispenserService.dispenseFuel("Petrol", 500.0);

        assertEquals(4500.0, result.getCurrentQuantity());
        verify(tankRepository).findByFuelName("Petrol");
        verify(tankService).dispenseFuel(1L, 500.0);
    }


    @Test
    void shouldThrowException_WhenFuelNameIsNull() {
        assertThrows(IllegalArgumentException.class, () -> dispenserService.dispenseFuel(null, 500.0));
    }

    @Test
    void shouldThrowException_WhenFuelNameIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> dispenserService.dispenseFuel("", 500.0));
    }

    @Test
    void shouldThrowException_WhenFuelNameIsWhitespace() {
        assertThrows(IllegalArgumentException.class, () -> dispenserService.dispenseFuel("   ", 500.0));
    }

    @Test
    void shouldThrowException_WhenLitersIsNull() {
        assertThrows(IllegalArgumentException.class, () -> dispenserService.dispenseFuel("Petrol", null));
    }

    @Test
    void shouldThrowException_WhenLitersIsZero() {
        assertThrows(IllegalArgumentException.class, () -> dispenserService.dispenseFuel("Petrol", 0.0));
    }

    @Test
    void shouldThrowException_WhenLitersIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> dispenserService.dispenseFuel("Petrol", -100.0));
    }

    @Test
    void shouldThrowException_WhenInsufficientFuelInTank() {
        Fuel fuel = new Fuel(1L, "Petrol", new BigDecimal("1.50"));
        Tank tank = new Tank(1L, 10000.0, 500.0, fuel);

        when(tankRepository.findByFuelName("Petrol")).thenReturn(Optional.of(tank));
        when(tankService.dispenseFuel(1L, 1000.0))
                .thenThrow(new IllegalArgumentException(
                        "Insufficient fuel in tank. Available: 500.0Liters, Requested: 1000.0Liters"));

        assertThrows(IllegalArgumentException.class, () -> dispenserService.dispenseFuel("Petrol", 1000.0));
        verify(tankRepository).findByFuelName("Petrol");
        verify(tankService).dispenseFuel(1L, 1000.0);
    }
}
