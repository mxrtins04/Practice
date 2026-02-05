package com.mxr.mfds.dto;

public class RefillRequest {
    private String fuelName;
    private Double litersToAdd;

    public String getFuelName() {
        return fuelName;
    }

    public void setFuelName(String fuelName) {
        this.fuelName = fuelName;
    }

    public Double getLitersToAdd() {
        return litersToAdd;
    }

    public void setLitersToAdd(Double litersToAdd) {
        this.litersToAdd = litersToAdd;
    }
}
