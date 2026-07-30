package net.sirgrantd.magic_coins.network.payload;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.sirgrantd.magic_coins.MagicCoinsMod;
import net.sirgrantd.magic_coins.client.ClientSyncedConfig;

public record SyncServerConfigS2C(
        double silverCoinsValue,
        double goldCoinsValue,
        double crystalCoinsValue) implements CustomPacketPayload {

    public static final Type<SyncServerConfigS2C> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(MagicCoinsMod.MOD_ID, "sync_server_config"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SyncServerConfigS2C> STREAM_CODEC = StreamCodec.of(
            (buf, msg) -> {
                buf.writeDouble(msg.silverCoinsValue());
                buf.writeDouble(msg.goldCoinsValue());
                buf.writeDouble(msg.crystalCoinsValue());
            },
            (buf) -> new SyncServerConfigS2C(
                    buf.readDouble(),
                    buf.readDouble(),
                    buf.readDouble()));

    @Override
    public Type<SyncServerConfigS2C> type() {
        return TYPE;
    }

    public static void handle(final SyncServerConfigS2C msg, final IPayloadContext ctx) {
        if (ctx.flow() != PacketFlow.CLIENTBOUND) {
            return;
        }

        ctx.enqueueWork(() -> applyClient(msg));
    }

    @OnlyIn(Dist.CLIENT)
    private static void applyClient(SyncServerConfigS2C msg) {
        ClientSyncedConfig.apply(
                msg.silverCoinsValue(),
                msg.goldCoinsValue(),
                msg.crystalCoinsValue());
    }
}
