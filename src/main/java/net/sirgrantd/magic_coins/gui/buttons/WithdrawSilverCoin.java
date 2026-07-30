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
public record WithdrawSilverCoin(int x, int y, int z) implements CustomPacketPayload, ButtonActionsPayload {

    public static final Type<WithdrawSilverCoin> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(MagicCoinsMod.MOD_ID, "withdraw_silver_coin_button"));

    public static final StreamCodec<RegistryFriendlyByteBuf, WithdrawSilverCoin> STREAM_CODEC =
        ButtonActionsPayloadCodec.codec(WithdrawSilverCoin::new);

    @Override
    public Type<WithdrawSilverCoin> type() {
        return TYPE;
    }

    public static void handleData(final WithdrawSilverCoin message, final IPayloadContext context) {
        ButtonPayloadHandler.handle(message, context, WithdrawSilverCoin::handleButtonAction);
    }

    public static void handleButtonAction(Player player, WithdrawSilverCoin message) {
        EconomyEventProvider economy = SGEconomyApi.get();

        double valueCoins = MagicCoinsApi.getValueSilverCoins();

        if (economy.isDecimalSystem()) {
            double totalCoins = economy.getBalance(player);

            if (totalCoins >= valueCoins) {
                economy.withdrawBalance(player, valueCoins);
                
                ItemStack silverCoin = new ItemStack(ItemsInit.SILVER_COIN.get());
                if (!player.addItem(silverCoin)) {
                    player.drop(silverCoin, false);
                }
            }
        } else {
            int totalCoins = economy.getBalanceAsInt(player);

            if (totalCoins >= valueCoins) {
                economy.withdrawBalanceAsInt(player, (int) valueCoins);

                ItemStack silverCoin = new ItemStack(ItemsInit.SILVER_COIN.get());
                if (!player.addItem(silverCoin)) {
                    player.drop(silverCoin, false);
                }
            }
        }
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        MagicNetwork.addNetworkMessage(WithdrawSilverCoin.TYPE, WithdrawSilverCoin.STREAM_CODEC,
                WithdrawSilverCoin::handleData);
    }

}