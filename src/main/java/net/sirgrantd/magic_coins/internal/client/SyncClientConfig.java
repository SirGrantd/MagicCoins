package net.sirgrantd.magic_coins.internal.client;

public class SyncClientConfig {
    private static double silverCoinValue;
    private static double goldCoinValue;
    private static double crystalCoinValue;

    private SyncClientConfig() {
    }

    public static double getSilverCoinValue() {
        return silverCoinValue;
    }

    public static double getGoldCoinValue() {
        return goldCoinValue;
    }

    public static double getCrystalCoinValue() {
        return crystalCoinValue;
    }

    public static void apply(double silverCoinValue, double goldCoinValue, double crystalCoinValue) {
        SyncClientConfig.silverCoinValue = silverCoinValue;
        SyncClientConfig.goldCoinValue = goldCoinValue;
        SyncClientConfig.crystalCoinValue = crystalCoinValue;
    }
}
