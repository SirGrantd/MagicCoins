package net.sirgrantd.magic_coins.api;

import net.sirgrantd.magic_coins.config.ServerConfig;

public class MagicCoinsApi {

    // Feature: Coins values

    public static double getValueSilverCoins() {
        return ServerConfig.silverCoinsValue;
    }

    public static double getValueGoldCoins() {
        return ServerConfig.goldCoinsValue;
    }

    public static double getValueCrystalCoins() {
        return ServerConfig.crystalCoinsValue;
    }
}