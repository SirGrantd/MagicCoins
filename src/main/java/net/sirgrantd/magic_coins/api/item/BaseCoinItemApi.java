package net.sirgrantd.magic_coins.api.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.level.Level;
import net.sirgrantd.magic_coins.internal.init.MagicCoinsSounds;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.sirgrantd.sg_economy.api.SGEconomyApi;

public abstract class BaseCoinItemApi extends Item {
    public BaseCoinItemApi(Properties properties) {
        super(properties.stacksTo(DEFAULT_MAX_STACK_SIZE).fireResistant());
    }

    protected abstract double getCoinValue();

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (!level.isClientSide()) {
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    MagicCoinsSounds.MAGIC_BAG_COLLECT_COINS.get(),
                    player.getSoundSource(), 1.0F, 1.0F);
        }

        boolean isShiftKeyDown = player.isShiftKeyDown();

        int count = !isShiftKeyDown ? itemStack.getCount() : 1;
        double getCurrentCoinValue = getCoinValue() * count;
        boolean isSuccessful = SGEconomyApi.depositBalance(player, getCurrentCoinValue);

        if (isSuccessful) {
            itemStack.shrink(count);
            return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
        }

        return InteractionResultHolder.pass(itemStack);
    }
}
