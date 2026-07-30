package net.sirgrantd.magic_coins.network.helper;

import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.sirgrantd.magic_coins.network.payload.ButtonActionsPayload;

public final class ButtonPayloadHandler {
    private ButtonPayloadHandler() {}

    @FunctionalInterface
    public interface Action<T extends ButtonActionsPayload> {
        void run(Player player, T message);
    }

    public static <T extends ButtonActionsPayload> void handle(T message, IPayloadContext context, Action<T> action) {
        if (context.flow() != PacketFlow.SERVERBOUND) return;

        context.enqueueWork(() -> action.run(context.player(), message)).exceptionally(e -> {
            context.connection().disconnect(Component.literal(e.getMessage()));
            return null;
        });
    }
}