package net.sirgrantd.magic_coins.internal.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.sirgrantd.magic_coins.api.MagicCoinsApi;
import net.sirgrantd.magic_coins.api.item.BaseCoinItemApi;

public class CrystalCoinItem extends BaseCoinItemApi {
    public CrystalCoinItem(Item.Properties properties) {
        super(properties.rarity(Rarity.EPIC));
    }

    @Override
    protected double getCoinValue() {
        return MagicCoinsApi.CoinsValues.getValueCrystalCoin();
    }
}
