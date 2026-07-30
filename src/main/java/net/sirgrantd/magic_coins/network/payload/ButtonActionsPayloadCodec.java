package net.sirgrantd.magic_coins.network.payload;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;


public final class ButtonActionsPayloadCodec {
    private ButtonActionsPayloadCodec() {}

    @FunctionalInterface
    public interface Factory<T extends ButtonActionsPayload> {
        T create(int x, int y, int z);
    }

    public static <T extends ButtonActionsPayload> StreamCodec<RegistryFriendlyByteBuf, T> codec(Factory<T> factory) {
        return StreamCodec.of(
            (buf, msg) -> {
                buf.writeInt(msg.x());
                buf.writeInt(msg.y());
                buf.writeInt(msg.z());
            },
            buf -> factory.create(buf.readInt(), buf.readInt(), buf.readInt())
        );
    }
}