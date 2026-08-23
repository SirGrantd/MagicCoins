package net.sirgrantd.magic_coins.api.utils;

import java.util.function.DoubleSupplier;

import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.sirgrantd.magic_coins.internal.client.SyncClientConfig;
import net.sirgrantd.magic_coins.internal.config.ServerConfig;

public class CoinsValuesUtils {

    private static double verifyEnv(DoubleSupplier clientValue, double serverValue) {
        return FMLEnvironment.dist == Dist.CLIENT ? clientValue.getAsDouble() : serverValue;
    }

    // --- SILVER ---
    public static double getValueSilverCoin(Level level) {
        return level.isClientSide() ? SyncClientConfig.getSilverCoinValue() : ServerConfig.silverCoinsValue;
    }

    public static double getValueSilverCoin() {
        return verifyEnv(SyncClientConfig::getSilverCoinValue, ServerConfig.silverCoinsValue);
    }

    // --- GOLD ---
    public static double getValueGoldCoin(Level level) {
        return level.isClientSide() ? SyncClientConfig.getGoldCoinValue() : ServerConfig.goldCoinsValue;
    }

    public static double getValueGoldCoin() {
        return verifyEnv(SyncClientConfig::getGoldCoinValue, ServerConfig.goldCoinsValue);
    }

    // --- CRYSTAL ---
    public static double getValueCrystalCoin(Level level) {
        return level.isClientSide() ? SyncClientConfig.getCrystalCoinValue() : ServerConfig.crystalCoinsValue;
    }

    public static double getValueCrystalCoin() {
        return verifyEnv(SyncClientConfig::getCrystalCoinValue, ServerConfig.crystalCoinsValue);
    }

}
