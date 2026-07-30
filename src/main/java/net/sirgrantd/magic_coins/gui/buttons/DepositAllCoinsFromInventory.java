package net.sirgrantd.magic_coins.gui.buttons;

import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;

import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.sirgrantd.magic_coins.MagicCoinsMod;
import net.sirgrantd.magic_coins.api.MagicCoinsApi;
import net.sirgrantd.magic_coins.init.ItemsInit;
import net.sirgrantd.magic_coins.network.MagicNetwork;
import net.sirgrantd.magic_coins.network.helper.ButtonPayloadHandler;
import net.sirgrantd.magic_coins.network.payload.ButtonActionsPayload;
import net.sirgrantd.magic_coins.network.payload.ButtonActionsPayloadCodec;
import net.sirgrantd.magic_coins.utils.Utils;
import net.sirgrantd.sg_economy.api.EconomyEventProvider;
import net.sirgrantd.sg_economy.api.SGEconomyApi;

@EventBusSubscriber
public record DepositAllCoinsFromInventory(int x, int y, int z) implements CustomPacketPayload, ButtonActionsPayload {

    public static final Type<DepositAllCoinsFromInventory> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(MagicCoinsMod.MOD_ID, "deposit_all_coins_from_inventory_button"));

    public static final StreamCodec<RegistryFriendlyByteBuf, DepositAllCoinsFromInventory> STREAM_CODEC =
        ButtonActionsPayloadCodec.codec(DepositAllCoinsFromInventory::new);

    @Override
    public Type<DepositAllCoinsFromInventory> type() {
        return TYPE;
    }

    public static void handleData(final DepositAllCoinsFromInventory message, final IPayloadContext context) {
        ButtonPayloadHandler.handle(message, context, DepositAllCoinsFromInventory::handleButtonAction);
    }

    public static void handleButtonAction(Player player, DepositAllCoinsFromInventory message) {
        if (player == null) {
            return;
        }

        if (player.getCapability(Capabilities.ItemHandler.ENTITY,
                null) instanceof IItemHandlerModifiable itemHandlerModifiable) {
            EconomyEventProvider economy = SGEconomyApi.get();

            int silverCoins = Utils.countItems(itemHandlerModifiable, ItemsInit.SILVER_COIN.get());
            int goldCoins = Utils.countItems(itemHandlerModifiable, ItemsInit.GOLD_COIN.get());
            int crystalCoins = Utils.countItems(itemHandlerModifiable, ItemsInit.CRYSTAL_COIN.get());

            Utils.removeItemsFromInventory(player, ItemsInit.SILVER_COIN.get(), silverCoins);
            Utils.removeItemsFromInventory(player, ItemsInit.GOLD_COIN.get(), goldCoins);
            Utils.removeItemsFromInventory(player, ItemsInit.CRYSTAL_COIN.get(), crystalCoins);

            double totalCoins = silverCoins * MagicCoinsApi.getValueSilverCoins() +
                    goldCoins * MagicCoinsApi.getValueGoldCoins() +
                    crystalCoins * MagicCoinsApi.getValueCrystalCoins();

            if (economy.isDecimalSystem()) {
                economy.depositBalance(player, totalCoins);
            } else {
                economy.depositBalanceAsInt(player, (int) totalCoins);
            }
        }
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        MagicNetwork.addNetworkMessage(DepositAllCoinsFromInventory.TYPE, DepositAllCoinsFromInventory.STREAM_CODEC,
                DepositAllCoinsFromInventory::handleData);
    }

}