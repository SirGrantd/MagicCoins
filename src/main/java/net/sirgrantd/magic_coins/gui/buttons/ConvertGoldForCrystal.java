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
public record ConvertGoldForCrystal(int x, int y, int z) implements CustomPacketPayload, ButtonActionsPayload {

    public static final Type<ConvertGoldForCrystal> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(MagicCoinsMod.MOD_ID, "convert_gold_for_crystal_button"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ConvertGoldForCrystal> STREAM_CODEC = ButtonActionsPayloadCodec
            .codec(ConvertGoldForCrystal::new);

    @Override
    public Type<ConvertGoldForCrystal> type() {
        return TYPE;
    }

    public static void handleData(final ConvertGoldForCrystal message, final IPayloadContext context) {
        ButtonPayloadHandler.handle(message, context, ConvertGoldForCrystal::handleButtonAction);
    }

    public static void handleButtonAction(Player player, ConvertGoldForCrystal message) {
        if (player == null) {
            return;
        }

        if (player.getCapability(Capabilities.ItemHandler.ENTITY,
                null) instanceof IItemHandlerModifiable itemHandlerModifiable) {
            double goldCoins = Utils.countItems(itemHandlerModifiable, ItemsInit.GOLD_COIN.get())
                    * MagicCoinsApi.getValueGoldCoins();

            if (goldCoins >= MagicCoinsApi.getValueCrystalCoins()) {

                int crystalCoins = (int) (goldCoins / MagicCoinsApi.getValueCrystalCoins());
                int remainingGoldCoins = (int) (goldCoins % MagicCoinsApi.getValueCrystalCoins());

                Utils.removeItemsFromInventory(player, ItemsInit.GOLD_COIN.get(),
                        (int) ((goldCoins / MagicCoinsApi.getValueGoldCoins())
                                - (remainingGoldCoins / MagicCoinsApi.getValueGoldCoins())));

                ItemStack crystalCoin = new ItemStack(ItemsInit.CRYSTAL_COIN.get(), crystalCoins);
                if (!player.getInventory().add(crystalCoin)) {
                    player.drop(crystalCoin, false);
                }
            }
        }

    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        MagicNetwork.addNetworkMessage(ConvertGoldForCrystal.TYPE, ConvertGoldForCrystal.STREAM_CODEC,
                ConvertGoldForCrystal::handleData);
    }
}
