package net.sirgrantd.magic_coins.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class ClientSyncedConfig {
    private static double silverCoinsValue = 1;
    private static double goldCoinsValue = 50;
    private static double crystalCoinsValue = 2500;

    private ClientSyncedConfig() {
    }

    public static double getSilverCoinsValue() {
        return silverCoinsValue;
    }

    public static double getGoldCoinsValue() {
        return goldCoinsValue;
    }

    public static double getCrystalCoinsValue() {
        return crystalCoinsValue;
    }

    public static void apply(double silverCoinsValue, double goldCoinsValue, double crystalCoinsValue) {
        ClientSyncedConfig.silverCoinsValue = silverCoinsValue;
        ClientSyncedConfig.goldCoinsValue = goldCoinsValue;
        ClientSyncedConfig.crystalCoinsValue = crystalCoinsValue;
    }
}