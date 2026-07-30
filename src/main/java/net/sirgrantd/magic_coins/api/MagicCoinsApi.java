package net.sirgrantd.magic_coins.api;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.sirgrantd.magic_coins.client.ClientSyncedConfig;
import net.sirgrantd.magic_coins.config.ServerConfig;

public class MagicCoinsApi {

    // Feature: Coins values

    public static double getValueSilverCoins() {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            return ClientSyncedConfig.getSilverCoinsValue();
        }
        return ServerConfig.silverCoinsValue;
    }

    public static double getValueGoldCoins() {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            return ClientSyncedConfig.getGoldCoinsValue();
        }
        return ServerConfig.goldCoinsValue;
    }

    public static double getValueCrystalCoins() {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            return ClientSyncedConfig.getCrystalCoinsValue();
        }
        return ServerConfig.crystalCoinsValue;
    }
}