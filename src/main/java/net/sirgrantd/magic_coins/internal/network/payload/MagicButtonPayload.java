package net.sirgrantd.magic_coins.internal.network.payload;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.sirgrantd.magic_coins.MagicCoinsMod;

public record MagicButtonPayload(String actionName, int x, int y, int z, boolean isShiftDown)
                implements CustomPacketPayload {

        public static final Type<MagicButtonPayload> TYPE = new Type<>(
                        Identifier.fromNamespaceAndPath(MagicCoinsMod.MOD_ID, "button_action"));

        public static final StreamCodec<RegistryFriendlyByteBuf, MagicButtonPayload> STREAM_CODEC = StreamCodec
                        .composite(
                                        ByteBufCodecs.STRING_UTF8, MagicButtonPayload::actionName,
                                        ByteBufCodecs.INT, MagicButtonPayload::x,
                                        ByteBufCodecs.INT, MagicButtonPayload::y,
                                        ByteBufCodecs.INT, MagicButtonPayload::z,
                                        ByteBufCodecs.BOOL, MagicButtonPayload::isShiftDown,
                                        MagicButtonPayload::new);

        @Override
        public Type<? extends CustomPacketPayload> type() {
                return TYPE;
        }
}
