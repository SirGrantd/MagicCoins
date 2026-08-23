package net.sirgrantd.magic_coins.internal.network.payload;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.sirgrantd.magic_coins.MagicCoinsMod;

public record SyncServerConfigS2C(
                double silverCoinValue,
                double goldCoinValue,
                double crystalCoinValue) implements CustomPacketPayload {

        public static final Type<SyncServerConfigS2C> TYPE = new Type<>(
                        ResourceLocation.fromNamespaceAndPath(MagicCoinsMod.MOD_ID, "sync_server_config"));

        public static StreamCodec<RegistryFriendlyByteBuf, SyncServerConfigS2C> STREAM_CODEC = StreamCodec.composite(
                        ByteBufCodecs.DOUBLE, SyncServerConfigS2C::silverCoinValue,
                        ByteBufCodecs.DOUBLE, SyncServerConfigS2C::goldCoinValue,
                        ByteBufCodecs.DOUBLE, SyncServerConfigS2C::crystalCoinValue,
                        SyncServerConfigS2C::new);

        @Override
        public Type<SyncServerConfigS2C> type() {
                return TYPE;
        }

}
