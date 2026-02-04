package com.mxr.mfds.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mxr.mfds.entity.FuelAttendant;
import com.mxr.mfds.entity.Transaction;
import com.mxr.mfds.repository.FuelAttendantRepository;

@ExtendWith(MockitoExtension.class)
public class FuelAttendantServiceTest {

    @Mock
    private FuelAttendantRepository fuelAttendantRepository;

    @InjectMocks
    private FuelAttendantService fuelAttendantService;

    @Test
    void shouldReturnAttendant_WhenValidIdProvided() {
        FuelAttendant expectedAttendant = new FuelAttendant(1L, "John Doe", null);

        when(fuelAttendantRepository.findById(1L)).thenReturn(Optional.of(expectedAttendant));

        FuelAttendant result = fuelAttendantService.getAttendantById(1L);

        assertEquals(1L, result.getId());
        assertEquals("John Doe", result.getName());
        verify(fuelAttendantRepository).findById(1L);
    }

    @Test
    void shouldThrowException_WhenAttendantNotFoundById() {
        when(fuelAttendantRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> fuelAttendantService.getAttendantById(999L));
        verify(fuelAttendantRepository).findById(999L);
    }

    @Test
    void shouldReturnAttendant_WhenValidNameProvided() {
        FuelAttendant expectedAttendant = new FuelAttendant(1L, "Jane Smith", null);

        when(fuelAttendantRepository.findByName("Jane Smith")).thenReturn(Optional.of(expectedAttendant));

        FuelAttendant result = fuelAttendantService.getAttendantByName("Jane Smith");

        assertEquals(1L, result.getId());
        assertEquals("Jane Smith", result.getName());
        verify(fuelAttendantRepository).findByName("Jane Smith");
    }

    @Test
    void shouldThrowException_WhenAttendantNotFoundByName() {
        when(fuelAttendantRepository.findByName("NonExistent")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> fuelAttendantService.getAttendantByName("NonExistent"));
        verify(fuelAttendantRepository).findByName("NonExistent");
    }

    @Test
    void shouldReturnAllAttendants_WhenRequested() {
        List<FuelAttendant> expectedAttendants = List.of(
            new FuelAttendant(1L, "John Doe", null),
            new FuelAttendant(2L, "Jane Smith", null)
        );

        when(fuelAttendantRepository.findAll()).thenReturn(expectedAttendants);

        List<FuelAttendant> result = fuelAttendantService.getAllAttendants();

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
        verify(fuelAttendantRepository).findAll();
    }

    @Test
    void shouldCreateAttendant_WhenDetailsAreProvided() {
        FuelAttendant newAttendant = new FuelAttendant(null, "Bob Johnson", null);
        FuelAttendant savedAttendant = new FuelAttendant(1L, "Bob Johnson", null);

        when(fuelAttendantRepository.existsByName("Bob Johnson")).thenReturn(false);
        when(fuelAttendantRepository.save(newAttendant)).thenReturn(savedAttendant);

        FuelAttendant result = fuelAttendantService.createAttendant(newAttendant);

        assertEquals(1L, result.getId());
        assertEquals("Bob Johnson", result.getName());
        verify(fuelAttendantRepository).existsByName("Bob Johnson");
        verify(fuelAttendantRepository).save(newAttendant);
    }

    @Test
    void shouldThrowException_WhenCreatingDuplicateAttendant() {
        FuelAttendant duplicateAttendant = new FuelAttendant(null, "John Doe", null);

        when(fuelAttendantRepository.existsByName("John Doe")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> fuelAttendantService.createAttendant(duplicateAttendant));
        verify(fuelAttendantRepository).existsByName("John Doe");
    }

    @Test
    void shouldReturnTransactions_WhenValidAttendantIdProvided() {
        List<Transaction> transactions = List.of(
            new Transaction(),
            new Transaction()
        );
        FuelAttendant attendant = new FuelAttendant(1L, "John Doe", transactions);

        when(fuelAttendantRepository.findById(1L)).thenReturn(Optional.of(attendant));

        List<Transaction> result = fuelAttendantService.getAttendantTransactions(1L);

        assertEquals(2, result.size());
        verify(fuelAttendantRepository).findById(1L);
    }

    @Test
    void shouldThrowException_WhenAttendantNameIsEmpty() {
        FuelAttendant attendantWithEmptyName = new FuelAttendant(null, "", null);

        assertThrows(IllegalArgumentException.class, () -> fuelAttendantService.createAttendant(attendantWithEmptyName));
    }

    @Test
    void shouldThrowException_WhenAttendantNameIsNull() {
        FuelAttendant attendantWithNullName = new FuelAttendant(null, null, null);

        assertThrows(IllegalArgumentException.class, () -> fuelAttendantService.createAttendant(attendantWithNullName));
    }
}
