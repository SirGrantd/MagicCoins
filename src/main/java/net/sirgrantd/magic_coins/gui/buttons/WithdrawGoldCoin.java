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
public record WithdrawGoldCoin(int x, int y, int z) implements CustomPacketPayload, ButtonActionsPayload {

    public static final Type<WithdrawGoldCoin> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(MagicCoinsMod.MOD_ID, "withdraw_gold_coin_button"));

    public static final StreamCodec<RegistryFriendlyByteBuf, WithdrawGoldCoin> STREAM_CODEC =
        ButtonActionsPayloadCodec.codec(WithdrawGoldCoin::new);

    @Override
    public Type<WithdrawGoldCoin> type() {
        return TYPE;
    }

    public static void handleData(final WithdrawGoldCoin message, final IPayloadContext context) {
        ButtonPayloadHandler.handle(message, context, WithdrawGoldCoin::handleButtonAction);
    }

    public static void handleButtonAction(Player player, WithdrawGoldCoin message) {
        EconomyEventProvider economy = SGEconomyApi.get();

        double valueCoins = MagicCoinsApi.getValueGoldCoins();

        if (economy.isDecimalSystem()) {
            double totalCoins = economy.getBalance(player);

            if (totalCoins >= valueCoins) {
                economy.withdrawBalance(player, valueCoins);

                ItemStack goldCoin = new ItemStack(ItemsInit.GOLD_COIN.get());
                if (!player.addItem(goldCoin)) {
                    player.drop(goldCoin, false);
                }
            }
        } else {
            int totalCoins = economy.getBalanceAsInt(player);

            if (totalCoins >= valueCoins) {
                economy.withdrawBalanceAsInt(player, (int) valueCoins);

                ItemStack goldCoin = new ItemStack(ItemsInit.GOLD_COIN.get());
                if (!player.addItem(goldCoin)) {
                    player.drop(goldCoin, false);
                }
            }
        }
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        MagicNetwork.addNetworkMessage(WithdrawGoldCoin.TYPE, WithdrawGoldCoin.STREAM_CODEC,
                WithdrawGoldCoin::handleData);
    }

}