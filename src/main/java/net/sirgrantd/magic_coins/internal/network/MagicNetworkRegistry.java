package net.sirgrantd.magic_coins.internal.network;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.sirgrantd.celesthyd.internal.network.CelesthydPayloadHandler;
import net.sirgrantd.magic_coins.MagicCoinsMod;
import net.sirgrantd.magic_coins.internal.client.SyncClientConfig;
import net.sirgrantd.magic_coins.internal.gui.EMagicCoinsButton;
import net.sirgrantd.magic_coins.internal.network.payload.MagicButtonPayload;
import net.sirgrantd.magic_coins.internal.network.payload.SyncServerConfigS2C;

@EventBusSubscriber(modid = MagicCoinsMod.MOD_ID)
public final class MagicNetworkRegistry {

    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(MagicCoinsMod.MOD_ID);

        registrar.playToClient(
                SyncServerConfigS2C.TYPE,
                SyncServerConfigS2C.STREAM_CODEC,
                (payload, context) -> CelesthydPayloadHandler.handleClientBound(payload, context, (p, ctx) -> {
                    SyncClientConfig.apply(p.silverCoinValue(), p.goldCoinValue(), p.crystalCoinValue());
                }));

        registrar.playToServer(
                MagicButtonPayload.TYPE,
                MagicButtonPayload.STREAM_CODEC,
                (payload, context) -> CelesthydPayloadHandler.handleServerBound(payload, context, (p, player, ctx) -> {
                    if (ctx.player() instanceof ServerPlayer serverPlayer) {
                        try {
                            EMagicCoinsButton action = EMagicCoinsButton.valueOf(p.actionName());
                            action.executeOnServer(serverPlayer, p.isShiftDown());
                        } catch (IllegalArgumentException e) {
                            MagicCoinsMod.LOGGER.warn("Received unknown button action: " + p.actionName());
                        }
                    }
                }));
    }
}
