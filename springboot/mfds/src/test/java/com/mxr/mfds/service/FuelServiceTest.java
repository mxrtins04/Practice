package com.mxr.mfds.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

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
    void shouldCreateFuel_WhenNameAndPriceProvided(){
        Fuel fuel = fuelService.createFuel("Diesel", new BigDecimal(1.5));
        assertEquals("Diesel", fuel.getName());
        assertEquals(new BigDecimal(1.5), fuel.getPricePerLiter());
    }

    @Test
    void shouldAddFuelToRepository_WhenFuelIsCreated(){
        Fuel fuel = fuelService.createFuel("Diesel", new BigDecimal(1.5));
        when(fuelRepository.existsByName("Diesel")).thenReturn(true);
        assertTrue(fuelRepository.existsByName("Diesel"));
        verify(fuelRepository).save(any(Fuel.class));
        
    }

    @Test
    void shouldThrowException_WhenFuelAlreadyExists(){
        when(fuelRepository.existsByName("Diesel")).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> fuelService.createFuel("Diesel", new BigDecimal(1.5)));
    }

}