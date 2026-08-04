package net.sirgrantd.magic_coins.internal.init;

import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.sirgrantd.magic_coins.MagicCoinsMod;
import net.sirgrantd.magic_coins.internal.items.CrystalCoinItem;
import net.sirgrantd.magic_coins.internal.items.GoldCoinItem;
import net.sirgrantd.magic_coins.internal.items.SilverCoinItem;

public class MagicCoinsItems {
        public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MagicCoinsMod.MOD_ID);
        public static final DeferredItem<Item> SILVER_COIN = ITEMS.registerItem("silver_coin",
                        properties -> new SilverCoinItem(properties));
        public static final DeferredItem<Item> GOLD_COIN = ITEMS.registerItem("gold_coin",
                        properties -> new GoldCoinItem(properties));
        public static final DeferredItem<Item> CRYSTAL_COIN = ITEMS.registerItem("crystal_coin",
                        properties -> new CrystalCoinItem(properties));

        public static void register(IEventBus eventBus) {
                ITEMS.register(eventBus);
        }
}
