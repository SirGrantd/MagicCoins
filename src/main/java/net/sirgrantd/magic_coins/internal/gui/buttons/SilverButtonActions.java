package net.sirgrantd.magic_coins.internal.gui.buttons;

import net.minecraft.world.entity.player.Player;
import net.sirgrantd.celesthyd.api.CelesthydApi;
import net.sirgrantd.magic_coins.api.MagicCoinsApi;
import net.sirgrantd.magic_coins.internal.init.MagicCoinsItems;
import net.sirgrantd.sg_economy.api.SGEconomyApi;

public class SilverButtonActions {

    private static final double EPSILON = 0.000001;

    public static void executeLeftClick(Player player, boolean isShiftPressed) {
        if (player == null) {
            return;
        }

        withdrawSilverCoinsToInventory(player, isShiftPressed);
    }

    public static void executeRightClick(Player player, boolean isShiftPressed) {
        if (player == null) {
            return;
        }

        depositSilverCoinsFromInventory(player, isShiftPressed);
    }

    private static void withdrawSilverCoinsToInventory(Player player, boolean isShiftPressed) {
        double silverCoinValue = MagicCoinsApi.CoinsValues.getValueSilverCoin();
        double totalValuePlayerBalance = SGEconomyApi.getBalance(player);

        int freeSlots = MagicCoinsApi.CoinsCount.countSilverCoinsFreeForInventory(player);

        int maxWithdraw = (int) Math.min(freeSlots, 64);

        int withdrawAmount = (int) Math.min(maxWithdraw,
                Math.floor((totalValuePlayerBalance / silverCoinValue) + EPSILON));

        int amount = isShiftPressed ? withdrawAmount : 1;
        double totalValue = silverCoinValue * amount;

        boolean isSuccess = SGEconomyApi.withdrawBalance(player, totalValue);
        if (isSuccess) {
            CelesthydApi.Inventory.addItemsFromInventory(player, MagicCoinsItems.SILVER_COIN.get(), amount);
        }
    }

    private static void depositSilverCoinsFromInventory(Player player, boolean isShiftPressed) {
        int silverCoinsAmount = CelesthydApi.Inventory.countItems(player.getInventory(),
                MagicCoinsItems.SILVER_COIN.get());

        if (silverCoinsAmount <= 0) {
            return;
        }

        int maxDeposit = (int) Math.min(64, silverCoinsAmount);

        int amountToDeposit = isShiftPressed ? maxDeposit : 1;
        double totalValue = MagicCoinsApi.CoinsValues.getValueSilverCoin() * amountToDeposit;

        boolean isSuccess = SGEconomyApi.depositBalance(player, totalValue);

        if (isSuccess) {
            CelesthydApi.Inventory.removeItemsFromInventory(player, MagicCoinsItems.SILVER_COIN.get(), amountToDeposit);
        }
    }
}
