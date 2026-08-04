package net.sirgrantd.magic_coins.internal.gui.buttons;

import net.minecraft.world.entity.player.Player;
import net.sirgrantd.celesthyd.api.CelesthydApi;
import net.sirgrantd.magic_coins.api.MagicCoinsApi;
import net.sirgrantd.magic_coins.internal.init.MagicCoinsItems;
import net.sirgrantd.sg_economy.api.SGEconomyApi;

public class CrystalButtonActions {

    private static final double EPSILON = 0.000001;

    public static void executeLeftClick(Player player, boolean isShiftPressed) {
        if (player == null) {
            return;
        }

        WithdrawCrystalCoinsToInventory(player, isShiftPressed);
    }

    public static void executeRightClick(Player player, boolean isShiftPressed) {
        if (player == null) {
            return;
        }

        depositCrystalCoinsFromInventory(player, isShiftPressed);
    }

    private static void WithdrawCrystalCoinsToInventory(Player player, boolean isShiftPressed) {
        double crystalCoinValue = MagicCoinsApi.CoinsValues.getValueCrystalCoin();
        double totalValuePlayerBalance = SGEconomyApi.getBalance(player);

        if (totalValuePlayerBalance < crystalCoinValue) {
            return;
        }

        int freeSlots = MagicCoinsApi.CoinsCount.countCrystalCoinsFreeForInventory(player);

        int maxWithdraw = (int) Math.min(freeSlots, 64);

        int withdrawAmount = (int) Math.min(maxWithdraw,
                Math.floor((totalValuePlayerBalance / crystalCoinValue) + EPSILON));

        int amount = isShiftPressed ? withdrawAmount : 1;
        double totalValue = crystalCoinValue * amount;
        boolean isSuccess = SGEconomyApi.withdrawBalance(player, totalValue);
        if (isSuccess) {
            CelesthydApi.Inventory.addItemsFromInventory(player, MagicCoinsItems.CRYSTAL_COIN.get(), amount);
        }
    }

    private static void depositCrystalCoinsFromInventory(Player player, boolean isShiftPressed) {
        int crystalCoins = CelesthydApi.Inventory.countItems(player.getInventory(), MagicCoinsItems.CRYSTAL_COIN.get());

        if (crystalCoins <= 0) {
            return;
        }

        int maxDeposit = (int) Math.min(64, crystalCoins);

        int amountToDeposit = isShiftPressed ? maxDeposit : 1;
        double totalValue = MagicCoinsApi.CoinsValues.getValueCrystalCoin() * amountToDeposit;

        boolean isSuccess = SGEconomyApi.depositBalance(player, totalValue);

        if (isSuccess) {
            CelesthydApi.Inventory.removeItemsFromInventory(player, MagicCoinsItems.CRYSTAL_COIN.get(),
                    amountToDeposit);
        }
    }
}
