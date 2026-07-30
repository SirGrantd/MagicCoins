package net.sirgrantd.magic_coins.items;

import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.sirgrantd.sg_economy.api.EconomyEventProvider;
import net.sirgrantd.sg_economy.api.SGEconomyApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class ProsperityAmuletItem extends Item implements ICurioItem {
    public ProsperityAmuletItem() {
        super(new Item.Properties().stacksTo(1).fireResistant().rarity(Rarity.RARE));
    }
    
    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean isFoil(ItemStack itemstack) {
        return true;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemstack, Item.TooltipContext context, List<Component> list, TooltipFlag flag) {
        EconomyEventProvider event = SGEconomyApi.get();
        boolean isEffect = event.getPercentageBalanceSaveOnDeath() != 100;
        super.appendHoverText(itemstack, context, list, flag);

        if (isEffect) {
            list.add(Component.translatable("item.prosperity_amulet.description_0.effect_0").withStyle(style -> style.withItalic(true).withColor(ChatFormatting.GRAY)));
            list.add(Component.translatable("item.prosperity_amulet.description_1.effect_0").withStyle(style -> style.withItalic(true).withColor(ChatFormatting.GRAY)));
        } else {
            list.add(Component.translatable("item.prosperity_amulet.description_0.effect_1").withStyle(style -> style.withItalic(true).withColor(ChatFormatting.GRAY)));
            list.add(Component.translatable("item.prosperity_amulet.description_1.effect_1").withStyle(style -> style.withItalic(true).withColor(ChatFormatting.GRAY)));
        }
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        EconomyEventProvider event = SGEconomyApi.get();
        event.setBalanceLostOnDeath(slotContext.entity(), false);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        EconomyEventProvider event = SGEconomyApi.get();
        event.setBalanceLostOnDeath(slotContext.entity(), true);
    }
}
