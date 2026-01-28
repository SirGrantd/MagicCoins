package net.sirgrantd.magic_coins.api;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResultHolder;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import net.sirgrantd.magic_coins.init.SoundsInit;
import net.sirgrantd.sg_economy.api.SGEconomyApi;
import net.sirgrantd.sg_economy.api.economy.EconomyProvider;

public abstract class BaseCoinItemApi extends Item {

    /*
        If you want to add new coins to the mod or merge it with other mods, this class 
        allows you to create an item with all the functionalities of the mod's native coins.
    */

    public BaseCoinItemApi(Rarity rarity) {
        super(new Item.Properties().stacksTo(64).fireResistant().rarity(rarity));
    }

    protected abstract double getCoinValue();

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean isFoil(ItemStack itemstack) {
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        if (!world.isClientSide) {
            world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundsInit.MAGIC_BAG_COLLECT_COINS.get(), player.getSoundSource());
        }

        EconomyProvider economy = SGEconomyApi.getSGEconomy();

        ItemStack itemStack = player.getItemInHand(hand);
        int count = itemStack.getCount();
        
        if (economy.isDecimalCurrency()) {
            double totalCurrency = count * getCoinValue();
            economy.addCurrency(player, totalCurrency);
        } else {
            double totalCoins = count * getCoinValue();
            economy.addCoins(player, (int) totalCoins);
        }
        
        itemStack.shrink(count);
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }
}
