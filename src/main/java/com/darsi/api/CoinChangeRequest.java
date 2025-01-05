package com.darsi.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class CoinChangeRequest {
    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("10000.00")
    private final double targetAmount;

    public CoinChangeRequest(@JsonProperty("targetAmount") double targetAmount) {
        this.targetAmount = targetAmount;
    }

    public double getTargetAmount() {
        return targetAmount;
    }

}
