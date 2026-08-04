package net.sirgrantd.magic_coins.internal.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.sirgrantd.magic_coins.api.MagicCoinsApi;
import net.sirgrantd.magic_coins.api.item.BaseCoinItemApi;

public class GoldCoinItem extends BaseCoinItemApi {
    public GoldCoinItem(Item.Properties properties) {
        super(properties.rarity(Rarity.RARE));
    }

    @Override
    protected double getCoinValue() {
        return MagicCoinsApi.CoinsValues.getValueGoldCoin();
    }
}
