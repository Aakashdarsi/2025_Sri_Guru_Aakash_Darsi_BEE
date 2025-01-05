package com.darsi.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinCount {
    private final double denomination;
    private final int count;

    public CoinCount(
            @JsonProperty("denomination") double denomination,
            @JsonProperty("count") int count) {
        this.denomination = denomination;
        this.count = count;
    }

    @JsonProperty
    public double getDenomination() {
        return denomination;
    }

    @JsonProperty
    public int getCount() {
        return count;
    }
}
