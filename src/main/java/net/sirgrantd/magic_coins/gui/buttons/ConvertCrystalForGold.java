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
public record ConvertCrystalForGold(int x, int y, int z) implements CustomPacketPayload, ButtonActionsPayload {

    public static final Type<ConvertCrystalForGold> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(MagicCoinsMod.MOD_ID, "crystal_for_gold_button"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ConvertCrystalForGold> STREAM_CODEC = ButtonActionsPayloadCodec
            .codec(ConvertCrystalForGold::new);

    @Override
    public Type<ConvertCrystalForGold> type() {
        return TYPE;
    }

    public static void handleData(final ConvertCrystalForGold message, final IPayloadContext context) {
        ButtonPayloadHandler.handle(message, context, ConvertCrystalForGold::handleButtonAction);
    }

    public static void handleButtonAction(Player player, ConvertCrystalForGold message) {
        if (player == null) {
            return;
        }

        if (player.getCapability(Capabilities.ItemHandler.ENTITY,
                null) instanceof IItemHandlerModifiable itemHandlerModifiable) {
            double crystalCoins = Utils.countItems(itemHandlerModifiable, ItemsInit.CRYSTAL_COIN.get())
                    * MagicCoinsApi.getValueCrystalCoins();

            if (crystalCoins >= MagicCoinsApi.getValueGoldCoins()) {

                int goldCoins = (int) (crystalCoins / MagicCoinsApi.getValueGoldCoins());
                int remainingCrystalCoins = (int) (crystalCoins % MagicCoinsApi.getValueGoldCoins());

                Utils.removeItemsFromInventory(player, ItemsInit.CRYSTAL_COIN.get(),
                        (int) ((crystalCoins / MagicCoinsApi.getValueCrystalCoins())
                                - (remainingCrystalCoins / MagicCoinsApi.getValueCrystalCoins())));

                ItemStack goldCoin = new ItemStack(ItemsInit.GOLD_COIN.get(), goldCoins);
                if (!player.getInventory().add(goldCoin)) {
                    player.drop(goldCoin, false);
                }

            }
        }

    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        MagicNetwork.addNetworkMessage(ConvertCrystalForGold.TYPE, ConvertCrystalForGold.STREAM_CODEC,
                ConvertCrystalForGold::handleData);
    }
}
