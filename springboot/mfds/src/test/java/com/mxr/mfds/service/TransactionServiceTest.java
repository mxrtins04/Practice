package com.mxr.mfds.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mxr.mfds.entity.Dispenser;
import com.mxr.mfds.entity.Fuel;
import com.mxr.mfds.entity.FuelAttendant;
import com.mxr.mfds.entity.Tank;
import com.mxr.mfds.entity.Transaction;
import com.mxr.mfds.repository.TransactionRepository;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private DispenserService dispenserService;

    @Mock
    private FuelAttendantService fuelAttendantService;

    @Mock
    private FuelService fuelService;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void shouldCreateTransaction_WhenValidDataProvided() {
        Fuel fuel = new Fuel(1L, "Petrol", new BigDecimal("1.50"));
        FuelAttendant attendant = new FuelAttendant(1L, "John Doe", null);
        Dispenser dispenser = new Dispenser();

        Transaction expectedTransaction = new Transaction(
                1L, 50.0, new BigDecimal("75.00"), new BigDecimal("1.50"),
                LocalDateTime.now(), fuel, attendant, dispenser);

        when(transactionRepository.save(any(Transaction.class))).thenReturn(expectedTransaction);

        Transaction result = transactionService.createTransaction(
                50.0, new BigDecimal("75.00"), new BigDecimal("1.50"),
                fuel, attendant, dispenser);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(50.0, result.getLiters());
        assertEquals(new BigDecimal("75.00"), result.getAmount());
        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    void shouldThrowException_WhenTransactionNotFoundById() {
        when(transactionRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> transactionService.getTransactionById(999L));
        verify(transactionRepository).findById(999L);
    }

    @Test
    void shouldReturnTransaction_WhenValidIdProvided() {
        Fuel fuel = new Fuel(1L, "Petrol", new BigDecimal("1.50"));
        FuelAttendant attendant = new FuelAttendant(1L, "John Doe", null);
        Dispenser dispenser = new Dispenser();

        Transaction expectedTransaction = new Transaction(
                1L, 50.0, new BigDecimal("75.00"), new BigDecimal("1.50"),
                LocalDateTime.now(), fuel, attendant, dispenser);

        when(transactionRepository.findById(1L)).thenReturn(Optional.of(expectedTransaction));

        Transaction result = transactionService.getTransactionById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(50.0, result.getLiters());
        verify(transactionRepository).findById(1L);
    }

    @Test
    void shouldProcessSale_WhenValidSaleDataProvided() {
        Fuel fuel = new Fuel(1L, "Petrol", new BigDecimal("1.50"));
        FuelAttendant attendant = new FuelAttendant(1L, "John Doe", null);
        Dispenser dispenser = new Dispenser();
        dispenser.setId(1L);
        dispenser.setCurrentAttendant(attendant);
        Tank tank = new Tank(1L, 10000.0, 5000.0, fuel);

        Transaction expectedTransaction = new Transaction(
                1L, 50.0, new BigDecimal("75.00"), new BigDecimal("1.50"),
                LocalDateTime.now(), fuel, attendant, dispenser);

        when(dispenserService.getDispenserById(1L)).thenReturn(dispenser);
        when(fuelService.getFuelByName("Petrol")).thenReturn(fuel);
        when(dispenserService.dispenseFuel("Petrol", 50.0)).thenReturn(tank);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(expectedTransaction);

        Transaction result = transactionService.processSale(1L, "Petrol", 50.0);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(50.0, result.getLiters());
        assertEquals(new BigDecimal("75.00"), result.getAmount());
        verify(dispenserService).getDispenserById(1L);
        verify(fuelService).getFuelByName("Petrol");
        verify(dispenserService).dispenseFuel("Petrol", 50.0);
        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    void shouldThrowException_WhenProcessingSaleWithoutAttendant() {
        Dispenser dispenser = new Dispenser();
        dispenser.setId(1L);

        when(dispenserService.getDispenserById(1L)).thenReturn(dispenser);

        assertThrows(IllegalArgumentException.class, () -> transactionService.processSale(1L, "Petrol", 50.0));
        verify(dispenserService).getDispenserById(1L);
    
    }

    @Test
    void shouldThrowException_WhenLitersIsNegative() {
        Fuel fuel = new Fuel(1L, "Petrol", new BigDecimal("1.50"));
        FuelAttendant attendant = new FuelAttendant(1L, "John Doe", null);
        Dispenser dispenser = new Dispenser();

        assertThrows(IllegalArgumentException.class,
                () -> transactionService.createTransaction(-10.0, new BigDecimal("75.00"), new BigDecimal("1.50"),
                        fuel, attendant, dispenser));
    }

    @Test
    void shouldThrowException_WhenAmountIsNegative() {
        Fuel fuel = new Fuel(1L, "Petrol", new BigDecimal("1.50"));
        FuelAttendant attendant = new FuelAttendant(1L, "John Doe", null);
        Dispenser dispenser = new Dispenser();

        assertThrows(IllegalArgumentException.class,
                () -> transactionService.createTransaction(50.0, new BigDecimal("-75.00"), new BigDecimal("1.50"),
                        fuel, attendant, dispenser));
    }

    @Test
    void shouldThrowException_WhenPricePerLiterIsNegative() {
        Fuel fuel = new Fuel(1L, "Petrol", new BigDecimal("1.50"));
        FuelAttendant attendant = new FuelAttendant(1L, "John Doe", null);
        Dispenser dispenser = new Dispenser();

        assertThrows(IllegalArgumentException.class,
                () -> transactionService.createTransaction(50.0, new BigDecimal("75.00"), new BigDecimal("-1.50"),
                        fuel, attendant, dispenser));
    }

    @Test
    void shouldThrowException_WhenRequiredFieldsAreNull() {
        assertThrows(IllegalArgumentException.class,
                () -> transactionService.createTransaction(50.0, new BigDecimal("75.00"), new BigDecimal("1.50"),
                        null, null, null));
    }

    @Test
    void shouldReturnTransactions_WhenValidAttendantIdProvided() {
        List<Transaction> expectedTransactions = List.of(
                new Transaction(),
                new Transaction());

        when(transactionRepository.findByFuelAttendantId(1L)).thenReturn(expectedTransactions);

        List<Transaction> result = transactionService.getTransactionsByAttendant(1L);

        assertEquals(2, result.size());
        verify(transactionRepository).findByFuelAttendantId(1L);
    }

    @Test
    void shouldReturnTransactions_WhenValidDispenserIdProvided() {
        List<Transaction> expectedTransactions = List.of(
                new Transaction(),
                new Transaction());

        when(transactionRepository.findByDispenserId(1L)).thenReturn(expectedTransactions);

        List<Transaction> result = transactionService.getTransactionsByDispenser(1L);

        assertEquals(2, result.size());
        verify(transactionRepository).findByDispenserId(1L);
    }

    @Test
    void shouldReturnTransactions_WhenValidFuelIdProvided() {
        List<Transaction> expectedTransactions = List.of(
                new Transaction(),
                new Transaction());

        when(transactionRepository.findByFuelName("Petrol")).thenReturn(expectedTransactions);

        List<Transaction> result = transactionService.getTransactionsByFuel("Petrol");

        assertEquals(2, result.size());
        verify(transactionRepository).findByFuelName("Petrol");
    }
}
