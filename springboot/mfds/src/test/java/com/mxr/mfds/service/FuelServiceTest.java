package com.mxr.mfds.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import com.mxr.mfds.entity.Fuel;
import com.mxr.mfds.repository.FuelRepository;

@ExtendWith(MockitoExtension.class)
public class FuelServiceTest {

    @Mock
    private FuelRepository fuelRepository;

    @InjectMocks
    private FuelService fuelService;

    @Test
    void shouldCreateFuel_WhenNameAndPriceProvided() {
        Fuel fuel = fuelService.createFuel("Diesel", new BigDecimal(1.5));
        assertEquals("Diesel", fuel.getName());
        assertEquals(new BigDecimal(1.5), fuel.getPricePerLiter());
    }

    @Test
    void shouldAddFuelToRepository_WhenFuelIsCreated() {
        Fuel fuel = fuelService.createFuel("Diesel", new BigDecimal(1.5));
        when(fuelRepository.existsByName("Diesel")).thenReturn(true);
        assertTrue(fuelRepository.existsByName("Diesel"));
        verify(fuelRepository).save(any(Fuel.class));

    }

    @Test
    void shouldThrowException_WhenFuelAlreadyExists() {
        when(fuelRepository.existsByName("Diesel")).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> fuelService.createFuel("Diesel", new BigDecimal(1.5)));
    }

    @Test
    void shouldReturnFuel_WhenValidIdProvided() {
        Fuel expectedFuel = new Fuel(1L, "Petrol", new BigDecimal("1.50"));
        when(fuelRepository.findById(1L)).thenReturn(Optional.of(expectedFuel));

        Fuel result = fuelService.getFuelById(1L);

        assertEquals("Petrol", result.getName());
        assertEquals(new BigDecimal("1.50"), result.getPricePerLiter());
        verify(fuelRepository).findById(1L);
    }

    @Test
    void shouldThrowException_WhenFuelNotFoundById() {
        when(fuelRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> fuelService.getFuelById(999L));
        verify(fuelRepository).findById(999L);
    }

    @Test
    void shouldReturnFuel_WhenValidNameProvided() {
        Fuel expectedFuel = new Fuel(1L, "Diesel", new BigDecimal("1.80"));
        when(fuelRepository.findByName("Diesel")).thenReturn(Optional.of(expectedFuel));

        Fuel result = fuelService.getFuelByName("Diesel");

        assertEquals("Diesel", result.getName());
        assertEquals(new BigDecimal("1.80"), result.getPricePerLiter());
        verify(fuelRepository).findByName("Diesel");
    }

    @Test
    void shouldThrowException_WhenFuelNotFoundByName() {
        when(fuelRepository.findByName("NonExistent")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> fuelService.getFuelByName("NonExistent"));
        verify(fuelRepository).findByName("NonExistent");
    }

    @Test
    void shouldReturnAllFuels_WhenRequested() {
        List<Fuel> expectedFuels = List.of(
                new Fuel(1L, "Petrol", new BigDecimal("1.50")),
                new Fuel(2L, "Diesel", new BigDecimal("1.80")));
        when(fuelRepository.findAll()).thenReturn(expectedFuels);

        List<Fuel> result = fuelService.getAllFuels();

        assertEquals(2, result.size());
        assertEquals("Petrol", result.get(0).getName());
        assertEquals("Diesel", result.get(1).getName());
        verify(fuelRepository).findAll();
    }

    @Test
    void shouldUpdateFuel_WhenValidIdAndNewPriceProvided() {
        Fuel existingFuel = new Fuel(1L, "Petrol", new BigDecimal("100"));
        BigDecimal newPrice = new BigDecimal("300.0");
        Fuel updatedFuel = new Fuel(1L, "Petrol", newPrice);

        when(fuelRepository.findById(1L)).thenReturn(Optional.of(existingFuel));
        when(fuelRepository.save(any(Fuel.class))).thenReturn(updatedFuel);

        Fuel result = fuelService.updateFuelPrice(1L, newPrice);

        assertEquals(new BigDecimal("300.0"), result.getPricePerLiter());
        assertEquals(1L, result.getId());
        verify(fuelRepository).findById(1L);
        verify(fuelRepository).save(any(Fuel.class));
    }

    @Test
    void shouldThrowException_WhenUpdatingPriceForNonExistentFuel() {
        when(fuelRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> fuelService.updateFuelPrice(999L, new BigDecimal("200.0")));
        verify(fuelRepository).findById(999L);
    }

    @Test
    void shouldThrowException_WhenUpdatingNonExistentFuel() {
        Fuel fuelDetails = new Fuel(null, "New Name", new BigDecimal("200.0"));
        when(fuelRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> fuelService.updateFuel(999L, fuelDetails));
        verify(fuelRepository).findById(999L);
    }

    @Test
    void shouldDeleteFuel_WhenValidIdProvided() {
        when(fuelRepository.existsById(1L)).thenReturn(true);

        fuelService.deleteFuel(1L);

        verify(fuelRepository).existsById(1L);
        verify(fuelRepository).deleteById(1L);
    }

    @Test
    void shouldThrowException_WhenDeletingNonExistentFuel() {
        when(fuelRepository.existsById(999L)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> fuelService.deleteFuel(999L));
        verify(fuelRepository).existsById(999L);
    }

}