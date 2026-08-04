package net.sirgrantd.magic_coins.internal.gui.buttons;

import net.minecraft.world.entity.player.Player;
import net.sirgrantd.celesthyd.api.CelesthydApi;
import net.sirgrantd.magic_coins.MagicCoinsMod;
import net.sirgrantd.magic_coins.api.MagicCoinsApi;
import net.sirgrantd.magic_coins.internal.init.MagicCoinsItems;
import net.sirgrantd.sg_economy.api.SGEconomyApi;

public class BagButtonActions {

    private static final double EPSILON = 0.000001;

    public static void executeLeftClick(Player player, boolean isShiftPressed) {
        if (player == null) {
            return;
        }

        if (!isShiftPressed) {
            DepositAllCoinsFromInventory(player);
        } else {
            CompressCoinsInInventory(player);
        }
    }

    public static void executeRightClick(Player player, boolean isShiftPressed) {
        if (player == null) {
            return;
        }

        if (!isShiftPressed) {
            WithdrawAllCoinsToInventory(player);
        } else {
            CompressCoinsInInventory(player);
        }
    }

    private static void DepositAllCoinsFromInventory(Player player) {
        int silverCoins = CelesthydApi.Inventory.countItems(player.getInventory(), MagicCoinsItems.SILVER_COIN.get());
        int goldCoins = CelesthydApi.Inventory.countItems(player.getInventory(), MagicCoinsItems.GOLD_COIN.get());
        int crystalCoins = CelesthydApi.Inventory.countItems(player.getInventory(), MagicCoinsItems.CRYSTAL_COIN.get());

        double totalValue = (silverCoins * MagicCoinsApi.CoinsValues.getValueSilverCoin())
                + (goldCoins * MagicCoinsApi.CoinsValues.getValueGoldCoin())
                + (crystalCoins * MagicCoinsApi.CoinsValues.getValueCrystalCoin());

        if (totalValue <= 0) {
            return;
        }

        boolean isSuccess = SGEconomyApi.depositBalance(player, totalValue);

        if (isSuccess) {
            CelesthydApi.Inventory.removeItemsFromInventory(player, MagicCoinsItems.SILVER_COIN.get(), silverCoins);
            CelesthydApi.Inventory.removeItemsFromInventory(player, MagicCoinsItems.GOLD_COIN.get(), goldCoins);
            CelesthydApi.Inventory.removeItemsFromInventory(player, MagicCoinsItems.CRYSTAL_COIN.get(), crystalCoins);
        }
    }

    private static void WithdrawAllCoinsToInventory(Player player) {
        double balance = SGEconomyApi.getBalance(player);

        int desiredCrystal = (int) Math.floor((balance / MagicCoinsApi.CoinsValues.getValueCrystalCoin()) + EPSILON);
        int freeCrystalSlots = MagicCoinsApi.CoinsCount.countCrystalCoinsFreeForInventory(player);
        int actualCrystal = Math.min(desiredCrystal, freeCrystalSlots);
        balance -= actualCrystal * MagicCoinsApi.CoinsValues.getValueCrystalCoin();

        int desiredGold = (int) Math.floor((balance / MagicCoinsApi.CoinsValues.getValueGoldCoin()) + EPSILON);
        int freeGoldSlots = MagicCoinsApi.CoinsCount.countGoldCoinsFreeForInventory(player);
        int actualGold = Math.min(desiredGold, freeGoldSlots);
        balance -= actualGold * MagicCoinsApi.CoinsValues.getValueGoldCoin();

        int desiredSilver = (int) Math.floor((balance / MagicCoinsApi.CoinsValues.getValueSilverCoin()) + EPSILON);
        int freeSilverSlots = MagicCoinsApi.CoinsCount.countSilverCoinsFreeForInventory(player);
        int actualSilver = Math.min(desiredSilver, freeSilverSlots);
        balance -= actualSilver * MagicCoinsApi.CoinsValues.getValueSilverCoin();

        if (balance < 0) {
            MagicCoinsMod.LOGGER.error("Error calculating coin counts for player {}. Remaining balance: {}",
                    player.getName().getString(), balance);
            return;
        }

        double totalValueToWithdraw = (actualSilver * MagicCoinsApi.CoinsValues.getValueSilverCoin())
                + (actualGold * MagicCoinsApi.CoinsValues.getValueGoldCoin())
                + (actualCrystal * MagicCoinsApi.CoinsValues.getValueCrystalCoin());

        if (totalValueToWithdraw <= 0) {
            return;
        }

        boolean isSuccess = SGEconomyApi.withdrawBalance(player, totalValueToWithdraw);

        if (isSuccess) {
            if (actualCrystal > 0)
                CelesthydApi.Inventory.addItemsFromInventory(player, MagicCoinsItems.CRYSTAL_COIN.get(), actualCrystal);
            if (actualGold > 0)
                CelesthydApi.Inventory.addItemsFromInventory(player, MagicCoinsItems.GOLD_COIN.get(), actualGold);
            if (actualSilver > 0)
                CelesthydApi.Inventory.addItemsFromInventory(player, MagicCoinsItems.SILVER_COIN.get(), actualSilver);
        }
    }

    private static void CompressCoinsInInventory(Player player) {
        int silverCoins = CelesthydApi.Inventory.countItems(player.getInventory(), MagicCoinsItems.SILVER_COIN.get());
        int goldCoins = CelesthydApi.Inventory.countItems(player.getInventory(), MagicCoinsItems.GOLD_COIN.get());
        int crystalCoins = CelesthydApi.Inventory.countItems(player.getInventory(), MagicCoinsItems.CRYSTAL_COIN.get());

        double totalValue = (silverCoins * MagicCoinsApi.CoinsValues.getValueSilverCoin())
                + (goldCoins * MagicCoinsApi.CoinsValues.getValueGoldCoin())
                + (crystalCoins * MagicCoinsApi.CoinsValues.getValueCrystalCoin());

        if (totalValue <= 0)
            return;

        int crystalCoinCount = (int) Math
                .floor((totalValue / MagicCoinsApi.CoinsValues.getValueCrystalCoin()) + EPSILON);
        totalValue -= crystalCoinCount * MagicCoinsApi.CoinsValues.getValueCrystalCoin();

        int goldCoinCount = (int) Math.floor((totalValue / MagicCoinsApi.CoinsValues.getValueGoldCoin()) + EPSILON);
        totalValue -= goldCoinCount * MagicCoinsApi.CoinsValues.getValueGoldCoin();

        int silverCoinCount = (int) Math.floor((totalValue / MagicCoinsApi.CoinsValues.getValueSilverCoin()) + EPSILON);
        totalValue -= silverCoinCount * MagicCoinsApi.CoinsValues.getValueSilverCoin();

        if (totalValue < 0) {
            MagicCoinsMod.LOGGER.error("Error calculating coin counts for player {}. Total value: {}",
                    player.getName().getString(), totalValue);
            return;
        }

        CelesthydApi.Inventory.removeItemsFromInventory(player, MagicCoinsItems.SILVER_COIN.get(), silverCoins);
        CelesthydApi.Inventory.removeItemsFromInventory(player, MagicCoinsItems.GOLD_COIN.get(), goldCoins);
        CelesthydApi.Inventory.removeItemsFromInventory(player, MagicCoinsItems.CRYSTAL_COIN.get(), crystalCoins);

        if (crystalCoinCount > 0)
            CelesthydApi.Inventory.addItemsFromInventory(player, MagicCoinsItems.CRYSTAL_COIN.get(), crystalCoinCount);
        if (goldCoinCount > 0)
            CelesthydApi.Inventory.addItemsFromInventory(player, MagicCoinsItems.GOLD_COIN.get(), goldCoinCount);
        if (silverCoinCount > 0)
            CelesthydApi.Inventory.addItemsFromInventory(player, MagicCoinsItems.SILVER_COIN.get(), silverCoinCount);
    }
}
