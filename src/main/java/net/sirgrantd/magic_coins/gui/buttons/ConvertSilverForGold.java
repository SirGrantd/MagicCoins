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
public record ConvertSilverForGold(int x, int y, int z) implements CustomPacketPayload, ButtonActionsPayload {

    public static final Type<ConvertSilverForGold> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(MagicCoinsMod.MOD_ID, "convert_silver_for_gold_button"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ConvertSilverForGold> STREAM_CODEC = ButtonActionsPayloadCodec
            .codec(ConvertSilverForGold::new);

    @Override
    public Type<ConvertSilverForGold> type() {
        return TYPE;
    }

    public static void handleData(final ConvertSilverForGold message, final IPayloadContext context) {
        ButtonPayloadHandler.handle(message, context, ConvertSilverForGold::handleButtonAction);
    }

    public static void handleButtonAction(Player player, ConvertSilverForGold message) {
        if (player == null) {
            return;
        }

        if (player.getCapability(Capabilities.ItemHandler.ENTITY,
                null) instanceof IItemHandlerModifiable itemHandlerModifiable) {

            double silverCoins = Utils.countItems(itemHandlerModifiable, ItemsInit.SILVER_COIN.get());

            if (silverCoins >= MagicCoinsApi.getValueGoldCoins()) {

                int goldCoins = (int) (silverCoins / MagicCoinsApi.getValueGoldCoins());
                int remainingSilverCoins = (int) (silverCoins % MagicCoinsApi.getValueGoldCoins());

                Utils.removeItemsFromInventory(player, ItemsInit.SILVER_COIN.get(),
                        (int) (silverCoins - remainingSilverCoins));

                ItemStack goldCoin = new ItemStack(ItemsInit.GOLD_COIN.get(), goldCoins);
                if (!player.getInventory().add(goldCoin)) {
                    player.drop(goldCoin, false);
                }
            }
        }

    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        MagicNetwork.addNetworkMessage(ConvertSilverForGold.TYPE, ConvertSilverForGold.STREAM_CODEC,
                ConvertSilverForGold::handleData);
    }
}
