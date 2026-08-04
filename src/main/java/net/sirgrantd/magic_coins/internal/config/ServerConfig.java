package net.sirgrantd.magic_coins.internal.config;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.network.PacketDistributor;
import net.sirgrantd.magic_coins.MagicCoinsMod;
import net.sirgrantd.magic_coins.internal.network.payload.SyncServerConfigS2C;

@EventBusSubscriber(modid = MagicCoinsMod.MOD_ID)
public class ServerConfig {

        public static double goldCoinsValue;
        public static double crystalCoinsValue;
        public static double silverCoinsValue;
        public static boolean coinsLootChests;

        public static class Config {
                public static final ModConfigSpec.Builder CONFIG_BUILDER = new ModConfigSpec.Builder();

                public static final ModConfigSpec.ConfigValue<Double> SILVER_COIN_VALUE;
                public static final ModConfigSpec.ConfigValue<Double> GOLD_COIN_VALUE;
                public static final ModConfigSpec.ConfigValue<Double> CRYSTAL_COIN_VALUE;
                public static final ModConfigSpec.ConfigValue<Boolean> COINS_LOOT_CHESTS;

                static {
                        CONFIG_BUILDER.push("COINS_SILVER");

                        SILVER_COIN_VALUE = CONFIG_BUILDER
                                        .comment("The value of a silver coin")
                                        .comment(
                                                        "WARNING: When considering an economic system that operates only with integers, values are automatically rounded up. For example, a value of 1.5 is treated as 2.")
                                        .defineInRange("silverMagicValue", 1.0, 0.01, 1000000.0);

                        CONFIG_BUILDER.pop();

                        CONFIG_BUILDER.push("COINS_GOLD");

                        GOLD_COIN_VALUE = CONFIG_BUILDER
                                        .comment("The value of a gold coin")
                                        .comment(
                                                        "WARNING: When considering an economic system that operates only with integers, values are automatically rounded up. For example, a value of 1.5 is treated as 2.")
                                        .defineInRange("goldMagicValue", 50.0, 0.01, 1000000.0);

                        CONFIG_BUILDER.pop();

                        CONFIG_BUILDER.push("COINS_CRYSTAL");

                        CRYSTAL_COIN_VALUE = CONFIG_BUILDER
                                        .comment("The value of a crystal coin")
                                        .comment(
                                                        "WARNING: When considering an economic system that operates only with integers, values are automatically rounded up. For example, a value of 1.5 is treated as 2.")
                                        .defineInRange("crystalMagicValue", 2500.0, 0.01, 1000000.0);

                        CONFIG_BUILDER.pop();

                        CONFIG_BUILDER.push("LOOTS");

                        COINS_LOOT_CHESTS = CONFIG_BUILDER
                                        .comment("Should coins be lootable from chests")
                                        .define("coinsLootChests", true);

                        CONFIG_BUILDER.pop();

                        SPEC = CONFIG_BUILDER.build();
                }

                public static final ModConfigSpec SPEC;
        }

        public static void bakeConfig() {
                silverCoinsValue = Config.SILVER_COIN_VALUE.get();
                goldCoinsValue = Config.GOLD_COIN_VALUE.get();
                crystalCoinsValue = Config.CRYSTAL_COIN_VALUE.get();
                coinsLootChests = Config.COINS_LOOT_CHESTS.get();

                PacketDistributor.sendToAllPlayers(
                                new SyncServerConfigS2C(
                                                silverCoinsValue,
                                                goldCoinsValue,
                                                crystalCoinsValue));
        }

        @SubscribeEvent
        public static void onLoad(final ModConfigEvent.Loading event) {
                if (event.getConfig().getType() == ModConfig.Type.SERVER
                                && event.getConfig().getSpec() == Config.SPEC) {
                        bakeConfig();
                }
        }

        @SubscribeEvent
        public static void onReload(final ModConfigEvent.Reloading event) {
                if (event.getConfig().getType() == ModConfig.Type.SERVER
                                && event.getConfig().getSpec() == Config.SPEC) {
                        bakeConfig();
                }
        }
}
