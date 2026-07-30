package net.sirgrantd.magic_coins.network;

import net.neoforged.neoforge.network.PacketDistributor;

import net.sirgrantd.magic_coins.MagicCoinsMod;
import net.sirgrantd.magic_coins.config.ServerConfig;
import net.sirgrantd.magic_coins.network.payload.SyncServerConfigS2C;

public final class ServerConfigSync {
    private ServerConfigSync() {
    }

    public static void syncToAllPlayersNextTick() {

        MagicCoinsMod.queueServerWork(1, () -> {
            MagicCoinsMod.LOGGER.debug("Syncing server config to all players");

            PacketDistributor.sendToAllPlayers(new SyncServerConfigS2C(
                    ServerConfig.silverCoinsValue,
                    ServerConfig.goldCoinsValue,
                    ServerConfig.crystalCoinsValue));
        });
    }
}