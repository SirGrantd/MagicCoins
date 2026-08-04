package net.sirgrantd.magic_coins.internal.gui.buttons;

import net.minecraft.world.entity.player.Player;
import net.sirgrantd.celesthyd.api.CelesthydApi;
import net.sirgrantd.magic_coins.api.MagicCoinsApi;
import net.sirgrantd.magic_coins.internal.init.MagicCoinsItems;
import net.sirgrantd.sg_economy.api.SGEconomyApi;

public class GoldButtonActions {

    private static final double EPSILON = 0.000001;

    public static void executeLeftClick(Player player, boolean isShiftPressed) {
        if (player == null) {
            return;
        }

        withdrawGoldCoinsToInventory(player, isShiftPressed);
    }

    public static void executeRightClick(Player player, boolean isShiftPressed) {
        if (player == null) {
            return;
        }

        depositGoldCoinsFromInventory(player, isShiftPressed);
    }

    private static void withdrawGoldCoinsToInventory(Player player, boolean isShiftPressed) {
        double goldCoinValue = MagicCoinsApi.CoinsValues.getValueGoldCoin();
        double totalValuePlayerBalance = SGEconomyApi.getBalance(player);

        int freeSlots = MagicCoinsApi.CoinsCount.countGoldCoinsFreeForInventory(player);

        int maxWithdraw = (int) Math.min(freeSlots, 64);

        int withdrawAmount = (int) Math.min(maxWithdraw,
                Math.floor((totalValuePlayerBalance / goldCoinValue) + EPSILON));

        int amount = isShiftPressed ? withdrawAmount : 1;
        double totalValue = goldCoinValue * amount;
        boolean isSuccess = SGEconomyApi.withdrawBalance(player, totalValue);
        if (isSuccess) {
            CelesthydApi.Inventory.addItemsFromInventory(player, MagicCoinsItems.GOLD_COIN.get(), amount);
        }
    }

    private static void depositGoldCoinsFromInventory(Player player, boolean isShiftPressed) {
        int goldCoinsAmount = CelesthydApi.Inventory.countItems(player.getInventory(), MagicCoinsItems.GOLD_COIN.get());

        if (goldCoinsAmount <= 0) {
            return;
        }

        int maxDeposit = (int) Math.min(64, goldCoinsAmount);

        int amountToDeposit = isShiftPressed ? maxDeposit : 1;
        double totalValue = MagicCoinsApi.CoinsValues.getValueGoldCoin() * amountToDeposit;

        boolean isSuccess = SGEconomyApi.depositBalance(player, totalValue);

        if (isSuccess) {
            CelesthydApi.Inventory.removeItemsFromInventory(player, MagicCoinsItems.GOLD_COIN.get(), amountToDeposit);
        }
    }
}
