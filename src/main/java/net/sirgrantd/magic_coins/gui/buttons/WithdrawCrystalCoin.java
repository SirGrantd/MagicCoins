package net.sirgrantd.magic_coins.gui.buttons;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;

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
import net.sirgrantd.sg_economy.api.EconomyEventProvider;
import net.sirgrantd.sg_economy.api.SGEconomyApi;

@EventBusSubscriber
public record WithdrawCrystalCoin(int x, int y, int z) implements CustomPacketPayload, ButtonActionsPayload {

    public static final Type<WithdrawCrystalCoin> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(MagicCoinsMod.MOD_ID, "withdraw_crystal_coin_button"));

    public static final StreamCodec<RegistryFriendlyByteBuf, WithdrawCrystalCoin> STREAM_CODEC =
        ButtonActionsPayloadCodec.codec(WithdrawCrystalCoin::new);

    @Override
    public Type<WithdrawCrystalCoin> type() {
        return TYPE;
    }

    public static void handleData(final WithdrawCrystalCoin message, final IPayloadContext context) {
        ButtonPayloadHandler.handle(message, context, WithdrawCrystalCoin::handleButtonAction);
    }

    public static void handleButtonAction(Player player, WithdrawCrystalCoin message) {
        EconomyEventProvider economy = SGEconomyApi.get();

        double valueCoins = MagicCoinsApi.getValueCrystalCoins();

        if (economy.isDecimalSystem()) {
            double totalCoins = economy.getBalance(player);

            if (totalCoins >= valueCoins) {
                economy.withdrawBalance(player, valueCoins);

                ItemStack crystalCoin = new ItemStack(ItemsInit.CRYSTAL_COIN.get());
                if (!player.addItem(crystalCoin)) {
                    player.drop(crystalCoin, false);
                }
            }
        } else {
            int totalCoins = economy.getBalanceAsInt(player);

            if (totalCoins >= valueCoins) {
                economy.withdrawBalanceAsInt(player, (int) valueCoins);

                ItemStack crystalCoin = new ItemStack(ItemsInit.CRYSTAL_COIN.get());
                if (!player.addItem(crystalCoin)) {
                    player.drop(crystalCoin, false);
                }
            }
        }
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        MagicNetwork.addNetworkMessage(WithdrawCrystalCoin.TYPE, WithdrawCrystalCoin.STREAM_CODEC,
                WithdrawCrystalCoin::handleData);
    }

}