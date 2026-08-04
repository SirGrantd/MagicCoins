package net.sirgrantd.magic_coins.api.utils;

import net.minecraft.world.entity.player.Player;
import net.sirgrantd.celesthyd.api.CelesthydApi;
import net.sirgrantd.magic_coins.api.MagicCoinsApi;
import net.sirgrantd.magic_coins.internal.init.MagicCoinsItems;
import net.sirgrantd.sg_economy.api.SGEconomyApi;

public class CoinsCountUtil {

    public static int countSilverCoinsInMagicBag(Player player) {
        double totalBalance = SGEconomyApi.getBalance(player);
        return (int) Math.floor(totalBalance / MagicCoinsApi.CoinsValues.getValueSilverCoin());
    }

    public static int countSilverCoinsFreeForInventory(Player player) {
        int totalSilverCoinsInBag = countSilverCoinsInMagicBag(player);
        int freeSlotsForSilverCoins = CelesthydApi.Inventory.getAvailableSpaceForItem(player.getInventory(),
                MagicCoinsItems.SILVER_COIN.get());
        return Math.min(totalSilverCoinsInBag, freeSlotsForSilverCoins);
    }

    public static int countGoldCoinsInMagicBag(Player player) {
        double totalBalance = SGEconomyApi.getBalance(player);
        return (int) Math.floor(totalBalance / MagicCoinsApi.CoinsValues.getValueGoldCoin());
    }

    public static int countGoldCoinsFreeForInventory(Player player) {
        int totalGoldCoinsInBag = countGoldCoinsInMagicBag(player);
        int freeSlotsForGoldCoins = CelesthydApi.Inventory.getAvailableSpaceForItem(player.getInventory(),
                MagicCoinsItems.GOLD_COIN.get());
        return Math.min(totalGoldCoinsInBag, freeSlotsForGoldCoins);
    }

    public static int countCrystalCoinsInMagicBag(Player player) {
        double totalBalance = SGEconomyApi.getBalance(player);
        return (int) Math.floor(totalBalance / MagicCoinsApi.CoinsValues.getValueCrystalCoin());
    }

    public static int countCrystalCoinsFreeForInventory(Player player) {
        int totalCrystalCoinsInBag = countCrystalCoinsInMagicBag(player);
        int freeSlotsForCrystalCoins = CelesthydApi.Inventory.getAvailableSpaceForItem(player.getInventory(),
                MagicCoinsItems.CRYSTAL_COIN.get());
        return Math.min(totalCrystalCoinsInBag, freeSlotsForCrystalCoins);
    }

}
