package net.sirgrantd.magic_coins.internal.loots;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.sirgrantd.magic_coins.internal.config.ServerConfig;
import net.sirgrantd.magic_coins.internal.init.MagicCoinsLootsConditions;
import net.sirgrantd.magic_coins.internal.config.LootConfigManager;

import java.util.List;
import java.util.Optional;

public record MagicCoinsChestCondition(Optional<List<ResourceLocation>> lootTables, Optional<String> tier)
        implements LootItemCondition {

    public static final MapCodec<MagicCoinsChestCondition> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            ResourceLocation.CODEC.listOf().optionalFieldOf("loot_tables").forGetter(MagicCoinsChestCondition::lootTables),
            Codec.STRING.optionalFieldOf("tier").forGetter(MagicCoinsChestCondition::tier))
            .apply(inst, MagicCoinsChestCondition::new));

    @Override
    public boolean test(LootContext context) {
        if (!ServerConfig.coinsLootChests)
            return false;

        ResourceLocation currentLootTable = context.getQueriedLootTableId();
        if (currentLootTable == null)
            return false;

        boolean isValid = false;

        if (lootTables().isPresent() && lootTables().get().contains(currentLootTable)) {
            isValid = true;
        }

        if (!isValid && tier().isPresent() && !tier().get().isEmpty()) {
            switch (tier().get().toLowerCase()) {
                case "common":
                    isValid = LootConfigManager.COMMON_LOOTS.contains(currentLootTable);
                    break;
                case "uncommon":
                    isValid = LootConfigManager.UNCOMMON_LOOTS.contains(currentLootTable);
                    break;
                case "rare":
                    isValid = LootConfigManager.RARE_LOOTS.contains(currentLootTable);
                    break;
                case "epic":
                    isValid = LootConfigManager.EPIC_LOOTS.contains(currentLootTable);
                    break;
            }
        }

        return isValid;
    }

    @Override
    public LootItemConditionType getType() {
        return MagicCoinsLootsConditions.MAGIC_COINS_CHEST.get();
    }
}
