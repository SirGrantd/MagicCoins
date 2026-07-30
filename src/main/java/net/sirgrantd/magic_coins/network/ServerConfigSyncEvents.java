package net.sirgrantd.magic_coins.network;

import net.minecraft.server.level.ServerPlayer;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import net.sirgrantd.magic_coins.MagicCoinsMod;
import net.sirgrantd.magic_coins.config.ServerConfig;
import net.sirgrantd.magic_coins.network.payload.SyncServerConfigS2C;

@EventBusSubscriber(modid = MagicCoinsMod.MOD_ID)
public final class ServerConfigSyncEvents {

    private ServerConfigSyncEvents() {}

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        PacketDistributor.sendToPlayer(
                player,
                new SyncServerConfigS2C(
                        ServerConfig.silverCoinsValue,
                        ServerConfig.goldCoinsValue,
                        ServerConfig.crystalCoinsValue
                )
        );
    }
}