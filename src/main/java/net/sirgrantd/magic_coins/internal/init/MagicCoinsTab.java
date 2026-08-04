package net.sirgrantd.magic_coins.internal.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.sirgrantd.magic_coins.MagicCoinsMod;

public class MagicCoinsTab {
        public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister
                        .create(Registries.CREATIVE_MODE_TAB, MagicCoinsMod.MOD_ID);
        public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAGIC_COINS = REGISTRY
                        .register(MagicCoinsMod.MOD_ID,
                                        () -> CreativeModeTab.builder()
                                                        .title(Component.translatable(
                                                                        "item_group.magic_coins.magic_group"))
                                                        .icon(() -> new ItemStack(MagicCoinsItems.SILVER_COIN.get()))
                                                        .displayItems((parameters, tabData) -> {
                                                                tabData.accept(MagicCoinsItems.SILVER_COIN.get());
                                                                tabData.accept(MagicCoinsItems.GOLD_COIN.get());
                                                                tabData.accept(MagicCoinsItems.CRYSTAL_COIN.get());
                                                        }).build());
}
