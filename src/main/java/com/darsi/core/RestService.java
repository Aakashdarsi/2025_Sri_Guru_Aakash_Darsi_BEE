package com.darsi.core;
import com.darsi.api.CoinCount;
import com.google.inject.Singleton;
import java.util.*;
import java.util.stream.Collectors;

import com.darsi.api.CoinChange;
import com.darsi.api.CoinChangeRequest;
import com.darsi.api.CoinChangeResponse;

@Singleton
public class RestService implements CoinChange {

    private static final double MIN_AMOUNT = 0.0;
    private static final double MAX_AMOUNT = 10000.00;
    private static final double[] DENOMINATIONS = {0.01, 0.05, 0.1, 0.2, 0.5, 1, 2, 5, 10, 50, 100, 1000};
    private static final int[] DENOMINATIONS_IN_CENTS = Arrays.stream(DENOMINATIONS)
            .mapToInt(d -> (int) Math.round(d * 100))
            .toArray();

    @Override
    public CoinChangeResponse calculateMinimumCoins(CoinChangeRequest request) {
        try {
            validateAmount(request.getTargetAmount());

            int amountInCents = (int) Math.round(request.getTargetAmount() * 100);
            List<Integer> coinsInCents = calculateCoins(amountInCents);

            // Convert the list of coins to coin counts
            List<CoinCount> coinCounts = calculateCoinCounts(coinsInCents);

            return new CoinChangeResponse(coinCounts, "Calculation successful", true);
        } catch (IllegalArgumentException e) {
            return new CoinChangeResponse(null, e.getMessage(), false);
        }
    }

    private void validateAmount(double amount) {
        if (amount < MIN_AMOUNT || amount > MAX_AMOUNT) {
            throw new IllegalArgumentException(
                    String.format("Target amount must be between %.2f and %.2f", MIN_AMOUNT, MAX_AMOUNT));
        }
    }

    private List<Integer> calculateCoins(int amountInCents) {
        int[] dp = new int[amountInCents + 1];
        int[] coin = new int[amountInCents + 1];

        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;

        for (int i = 1; i <= amountInCents; i++) {
            for (int denom : DENOMINATIONS_IN_CENTS) {
                if (denom <= i && dp[i - denom] != Integer.MAX_VALUE) {
                    if (dp[i] > dp[i - denom] + 1) {
                        dp[i] = dp[i - denom] + 1;
                        coin[i] = denom;
                    }
                }
            }
        }

        if (dp[amountInCents] == Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Unable to make exact change for the given amount");
        }

        List<Integer> result = new ArrayList<>();
        int remaining = amountInCents;
        while (remaining > 0) {
            result.add(coin[remaining]);
            remaining -= coin[remaining];
        }

        return result;
    }

    private List<CoinCount> calculateCoinCounts(List<Integer> coinsInCents) {
        // Count occurrences of each coin
        Map<Integer, Integer> countMap = new HashMap<>();
        for (int coin : coinsInCents) {
            countMap.merge(coin, 1, Integer::sum);
        }

        // Convert to CoinCount objects
        List<CoinCount> coinCounts = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : countMap.entrySet()) {
            double denomination = entry.getKey() / 100.0;
            int count = entry.getValue();
            coinCounts.add(new CoinCount(denomination, count));
        }

        // Sort by denomination
        coinCounts.sort(Comparator.comparingDouble(CoinCount::getDenomination));

        return coinCounts;
    }

}
