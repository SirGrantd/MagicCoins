package net.sirgrantd.magic_coins.internal.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.sirgrantd.magic_coins.MagicCoinsMod;
import net.sirgrantd.magic_coins.internal.loots.MagicCoinsChestCondition;

import java.util.function.Supplier;

public class MagicCoinsLootsConditions {
    public static final DeferredRegister<LootItemConditionType> CONDITION_TYPES = DeferredRegister
            .create(Registries.LOOT_CONDITION_TYPE, MagicCoinsMod.MOD_ID);

    public static final Supplier<LootItemConditionType> MAGIC_COINS_CHEST = CONDITION_TYPES
            .register("chest_tier", () -> new LootItemConditionType(MagicCoinsChestCondition.CODEC));

    public static void register(IEventBus eventBus) {
        CONDITION_TYPES.register(eventBus);
    }
}
