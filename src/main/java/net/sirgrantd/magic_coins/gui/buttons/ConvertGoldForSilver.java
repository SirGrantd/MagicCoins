package net.sirgrantd.magic_coins.gui.buttons;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import net.sirgrantd.magic_coins.MagicCoinsMod;
import net.sirgrantd.magic_coins.api.MagicCoinsApi;
import net.sirgrantd.magic_coins.init.ItemsInit;
import net.sirgrantd.magic_coins.network.MagicNetwork;
import net.sirgrantd.magic_coins.network.helper.ButtonPayloadHandler;
import net.sirgrantd.magic_coins.network.payload.ButtonActionsPayload;
import net.sirgrantd.magic_coins.network.payload.ButtonActionsPayloadCodec;
import net.sirgrantd.magic_coins.utils.Utils;

@EventBusSubscriber
public record ConvertGoldForSilver(int x, int y, int z) implements CustomPacketPayload, ButtonActionsPayload {

    public static final Type<ConvertGoldForSilver> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(MagicCoinsMod.MOD_ID, "convert_gold_for_silver_button"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ConvertGoldForSilver> STREAM_CODEC = ButtonActionsPayloadCodec
            .codec(ConvertGoldForSilver::new);

    @Override
    public Type<ConvertGoldForSilver> type() {
        return TYPE;
    }

    public static void handleData(final ConvertGoldForSilver message, final IPayloadContext context) {
        ButtonPayloadHandler.handle(message, context, ConvertGoldForSilver::handleButtonAction);
    }

    public static void handleButtonAction(Player player, ConvertGoldForSilver message) {
        if (player == null) {
            return;
        }

        if (player.getCapability(Capabilities.ItemHandler.ENTITY,
                null) instanceof IItemHandlerModifiable itemHandlerModifiable) {
            double goldCoins = Utils.countItems(itemHandlerModifiable, ItemsInit.GOLD_COIN.get())
                    * MagicCoinsApi.getValueGoldCoins();

            if (goldCoins >= MagicCoinsApi.getValueSilverCoins()) {

                int silverCoins = (int) (goldCoins / MagicCoinsApi.getValueSilverCoins());
                int remainingGoldCoins = (int) (goldCoins % MagicCoinsApi.getValueSilverCoins());

                Utils.removeItemsFromInventory(player, ItemsInit.GOLD_COIN.get(),
                        (int) ((goldCoins / MagicCoinsApi.getValueGoldCoins())
                                - (remainingGoldCoins / MagicCoinsApi.getValueGoldCoins())));

                ItemStack silverCoin = new ItemStack(ItemsInit.SILVER_COIN.get(), silverCoins);
                if (!player.getInventory().add(silverCoin)) {
                    player.drop(silverCoin, false);
                }
            }
        }

    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        MagicNetwork.addNetworkMessage(ConvertGoldForSilver.TYPE, ConvertGoldForSilver.STREAM_CODEC,
                ConvertGoldForSilver::handleData);
    }
}
