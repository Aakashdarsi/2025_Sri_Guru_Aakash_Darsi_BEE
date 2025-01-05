package com.darsi.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CoinChangeResponse {
    private final List<CoinCount> coinCounts;
    private final String message;
    private final boolean success;

    public CoinChangeResponse(
            @JsonProperty("coinCounts") List<CoinCount> coinCounts,
            @JsonProperty("message") String message,
            @JsonProperty("success") boolean success) {
        this.coinCounts = coinCounts;
        this.message = message;
        this.success = success;
    }

    @JsonProperty
    public List<CoinCount> getCoinCounts() {
        return coinCounts;
    }

    @JsonProperty
    public String getMessage() {
        return message;
    }

    @JsonProperty
    public boolean isSuccess() {
        return success;
    }
}
