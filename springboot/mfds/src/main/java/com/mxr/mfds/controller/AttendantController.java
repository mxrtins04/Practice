package com.mxr.mfds.controller;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mxr.mfds.dto.AddFuelRequest;
import com.mxr.mfds.dto.AssignmentRequest;
import com.mxr.mfds.dto.DispenseRequest;
import com.mxr.mfds.dto.RefillRequest;
import com.mxr.mfds.dto.StatusResponse;
import com.mxr.mfds.entity.Dispenser;
import com.mxr.mfds.entity.Fuel;
import com.mxr.mfds.entity.FuelAttendant;
import com.mxr.mfds.entity.Transaction;
import com.mxr.mfds.service.DispenserService;
import com.mxr.mfds.service.FuelAttendantService;
import com.mxr.mfds.service.FuelService;
import com.mxr.mfds.service.TankService;
import com.mxr.mfds.service.TransactionService;

@RestController
@RequestMapping("/api/attendant")
public class AttendantController {

    @Autowired
    private DispenserService dispenserService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private FuelAttendantService fuelAttendantService;

    @Autowired
    private TankService tankService;

    @Autowired
    private FuelService fuelService;

    @PostMapping("/assign")
    public ResponseEntity<?> assignAttendant(@RequestBody AssignmentRequest request) {
        try {
            FuelAttendant attendant = fuelAttendantService.getAttendantById(request.getAttendantId());
            Dispenser dispenser = dispenserService.assignAttendantToDispenser(request.getDispenserId(),
                    attendant.getId());

            return ResponseEntity.ok().body(dispenser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // @PostMapping("/register")
    // public String registerAttendant(@RequestBody Map<String, Object> userMap) {
    //     String name = (String) userMap.get("name");
    //     String id = (String) userMap.get("id");
    //     String age = (String) userMap.get("age");
    //     return name + "," + id + "," + age;
    // }

    @GetMapping("/status")
    public ResponseEntity<?> getStatus(@RequestParam Long attendantId) {
        try {
            FuelAttendant attendant = fuelAttendantService.getAttendantById(attendantId);

            StatusResponse response = new StatusResponse();
            response.setAttendant(attendant);
            response.setFuelLevels(tankService.getAllTanks());

            return ResponseEntity.ok().body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/dispense")
    public ResponseEntity<?> dispenseFuel(@RequestBody DispenseRequest request) {
        try {
            Transaction transaction = transactionService.processSale(
                    1L,
                    request.getFuelName(),
                    request.getLiters());

            return ResponseEntity.ok().body(transaction);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/fuel/add")
    public ResponseEntity<?> addFuel(@RequestBody AddFuelRequest request) {
        try {
            Fuel fuel = fuelService.createFuel(request.getName(), request.getPricePerLiter());
            return ResponseEntity.ok().body(fuel);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/refill")
    public ResponseEntity<?> refillTank(@RequestBody RefillRequest request) {
        try {
            tankService.refillTank(request.getFuelName(), request.getLitersToAdd());
            return ResponseEntity.ok().body("Tank refilled successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/transactions")
    public ResponseEntity<?> getTransactions(@RequestParam Long attendantId,
            @RequestParam(required = false) String date) {
        try {
            FuelAttendant attendant = fuelAttendantService.getAttendantById(attendantId);
            List<Transaction> transactions = fuelAttendantService.getAttendantTransactions(attendant.getId());

            return ResponseEntity.ok().body(transactions);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/unassign")
    public ResponseEntity<?> unassignAttendant(@RequestBody AssignmentRequest request) {
        try {
            FuelAttendant attendant = fuelAttendantService.getAttendantById(request.getAttendantId());
            dispenserService.removeAttendantFromDispenser(1L);

            return ResponseEntity.ok().body("Attendant unassigned successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
