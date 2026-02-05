package com.mxr.mfds.dto;

import java.math.BigDecimal;

public class AddFuelRequest {
    private String name;
    private BigDecimal pricePerLiter;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPricePerLiter() {
        return pricePerLiter;
    }

    public void setPricePerLiter(BigDecimal pricePerLiter) {
        this.pricePerLiter = pricePerLiter;
    }
}
