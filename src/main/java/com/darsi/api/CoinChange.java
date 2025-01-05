package com.darsi.api;

public interface CoinChange {
    CoinChangeResponse calculateMinimumCoins(CoinChangeRequest request);

}
