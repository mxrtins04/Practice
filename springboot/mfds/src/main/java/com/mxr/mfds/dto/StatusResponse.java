package com.mxr.mfds.dto;

import java.util.List;

import com.mxr.mfds.entity.FuelAttendant;
import com.mxr.mfds.entity.Tank;

public class StatusResponse {
    private FuelAttendant attendant;
    private Tank dispenser;
    private List<Tank> fuelLevels;

    public FuelAttendant getAttendant() {
        return attendant;
    }

    public void setAttendant(FuelAttendant attendant) {
        this.attendant = attendant;
    }

    public Tank getDispenser() {
        return dispenser;
    }

    public void setDispenser(Tank dispenser) {
        this.dispenser = dispenser;
    }

    public List<Tank> getFuelLevels() {
        return fuelLevels;
    }

    public void setFuelLevels(List<Tank> fuelLevels) {
        this.fuelLevels = fuelLevels;
    }
}
