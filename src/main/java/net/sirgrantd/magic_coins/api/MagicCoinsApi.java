package net.sirgrantd.magic_coins.api;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.sirgrantd.magic_coins.api.utils.CoinsCountUtil;
import net.sirgrantd.magic_coins.api.utils.CoinsValuesUtils;

public class MagicCoinsApi {

    public static final class CoinsValues {

        private CoinsValues() {
        }

        public static double getValueSilverCoin(Level level) {
            return CoinsValuesUtils.getValueSilverCoin(level);
        }

        public static double getValueSilverCoin() {
            return CoinsValuesUtils.getValueSilverCoin();
        }

        public static double getValueGoldCoin(Level level) {
            return CoinsValuesUtils.getValueGoldCoin(level);
        }

        public static double getValueGoldCoin() {
            return CoinsValuesUtils.getValueGoldCoin();
        }

        public static double getValueCrystalCoin(Level level) {
            return CoinsValuesUtils.getValueCrystalCoin(level);
        }

        public static double getValueCrystalCoin() {
            return CoinsValuesUtils.getValueCrystalCoin();
        }
    }

    public static final class CoinsCount {

        private CoinsCount() {
        }

        public static int countSilverCoinsInMagicBag(Player player) {
            return CoinsCountUtil.countSilverCoinsInMagicBag(player);
        }

        public static int countSilverCoinsFreeForInventory(Player player) {
            return CoinsCountUtil.countSilverCoinsFreeForInventory(player);
        }

        public static int countGoldCoinsInMagicBag(Player player) {
            return CoinsCountUtil.countGoldCoinsInMagicBag(player);
        }

        public static int countGoldCoinsFreeForInventory(Player player) {
            return CoinsCountUtil.countGoldCoinsFreeForInventory(player);
        }

        public static int countCrystalCoinsInMagicBag(Player player) {
            return CoinsCountUtil.countCrystalCoinsInMagicBag(player);
        }

        public static int countCrystalCoinsFreeForInventory(Player player) {
            return CoinsCountUtil.countCrystalCoinsFreeForInventory(player);
        }

    }

}
