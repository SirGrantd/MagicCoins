package net.sirgrantd.magic_coins.internal.config;

import java.util.function.Supplier;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

import net.sirgrantd.magic_coins.MagicCoinsMod;

@EventBusSubscriber(modid = MagicCoinsMod.MOD_ID, value = Dist.CLIENT)
public class ClientConfig {
        // Collect Coins Button
        public static int xCollectCoins, yCollectCoins, xCollectCoinsCreative, yCollectCoinsCreative;
        public static boolean enableConvertButtons;

        // Buttons
        public static int xSilverButton, ySilverButton, xSilverButtonCreative, ySilverButtonCreative;
        public static boolean enableSilverButton;

        public static int xGoldButton, yGoldButton, xGoldButtonCreative, yGoldButtonCreative;
        public static boolean enableGoldButton;

        public static int xCrystalButton, yCrystalButton, xCrystalButtonCreative, yCrystalButtonCreative;
        public static boolean enableCrystalButton;

        public static class Config {
                public static ModConfigSpec SPEC;
                // Collect Coins Button
                public static final Supplier<Integer> X_COLLECT_COINS;
                public static final Supplier<Integer> Y_COLLECT_COINS;
                public static final Supplier<Integer> X_COLLECT_COINS_CREATIVE;
                public static final Supplier<Integer> Y_COLLECT_COINS_CREATIVE;
                public static final Supplier<Boolean> ENABLE_CONVERT_BUTTONS;

                // Silver Button
                public static final Supplier<Boolean> ENABLE_SILVER_BUTTON;
                public static final Supplier<Integer> X_SILVER_BUTTON;
                public static final Supplier<Integer> Y_SILVER_BUTTON;
                public static final Supplier<Integer> X_SILVER_BUTTON_CREATIVE;
                public static final Supplier<Integer> Y_SILVER_BUTTON_CREATIVE;

                // Gold Button
                public static final Supplier<Boolean> ENABLE_GOLD_BUTTON;
                public static final Supplier<Integer> X_GOLD_BUTTON;
                public static final Supplier<Integer> Y_GOLD_BUTTON;
                public static final Supplier<Integer> X_GOLD_BUTTON_CREATIVE;
                public static final Supplier<Integer> Y_GOLD_BUTTON_CREATIVE;

                // Crystal Button
                public static final Supplier<Boolean> ENABLE_CRYSTAL_BUTTON;
                public static final Supplier<Integer> X_CRYSTAL_BUTTON;
                public static final Supplier<Integer> Y_CRYSTAL_BUTTON;
                public static final Supplier<Integer> X_CRYSTAL_BUTTON_CREATIVE;
                public static final Supplier<Integer> Y_CRYSTAL_BUTTON_CREATIVE;

                static {
                        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

                        // Collect Coins Button group
                        builder.push("COLLECT_COINS_BUTTON");
                        X_COLLECT_COINS = builder.comment("X position for coin button")
                                        .comment("Tips: 80 for align for right side")
                                        .defineInRange("xCollectCoins", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
                        X_COLLECT_COINS_CREATIVE = builder.comment("X position for coin button in creative mode")
                                        .comment("Tips: 80 for align for right side")
                                        .defineInRange("xCollectCoinsCreative", 0, Integer.MIN_VALUE,
                                                        Integer.MAX_VALUE);
                        Y_COLLECT_COINS = builder.comment("Y position for coin button").defineInRange("yCollectCoins",
                                        0,
                                        Integer.MIN_VALUE, Integer.MAX_VALUE);
                        Y_COLLECT_COINS_CREATIVE = builder.comment("Y position for coin button in creative mode")
                                        .defineInRange("yCollectCoinsCreative",
                                                        0,
                                                        Integer.MIN_VALUE, Integer.MAX_VALUE);
                        ENABLE_CONVERT_BUTTONS = builder.comment("Enable convert buttons on collect coins button")
                                        .define("enableConvertButtons", true);
                        builder.pop();

                        // Silver Button group
                        builder.push("SILVER_BUTTON");
                        ENABLE_SILVER_BUTTON = builder.comment("Enable silver button").define("enableSilverButton",
                                        true);
                        X_SILVER_BUTTON = builder.comment("X position of silver button").defineInRange("xSilverButton",
                                        0, -750,
                                        750);
                        X_SILVER_BUTTON_CREATIVE = builder.comment("X position of silver button in creative mode")
                                        .defineInRange("xSilverButtonCreative",
                                                        0, -750,
                                                        750);
                        Y_SILVER_BUTTON = builder.comment("Y position of silver button").defineInRange("ySilverButton",
                                        0, -750,
                                        750);
                        Y_SILVER_BUTTON_CREATIVE = builder.comment("Y position of silver button in creative mode")
                                        .defineInRange("ySilverButtonCreative",
                                                        0, -750,
                                                        750);
                        builder.pop();

                        // Gold Button group
                        builder.push("GOLD_BUTTON");
                        ENABLE_GOLD_BUTTON = builder.comment("Enable gold button").define("enableGoldButton", true);
                        X_GOLD_BUTTON = builder.comment("X position of gold button").defineInRange("xGoldButton", 0,
                                        -750, 750);
                        X_GOLD_BUTTON_CREATIVE = builder.comment("X position of gold button in creative mode")
                                        .defineInRange("xGoldButtonCreative", 0, -750, 750);
                        Y_GOLD_BUTTON = builder.comment("Y position of gold button").defineInRange("yGoldButton", 0,
                                        -750, 750);
                        Y_GOLD_BUTTON_CREATIVE = builder.comment("Y position of gold button in creative mode")
                                        .defineInRange("yGoldButtonCreative", 0, -750, 750);
                        builder.pop();

                        // Crystal Button group
                        builder.push("CRYSTAL_BUTTON");
                        ENABLE_CRYSTAL_BUTTON = builder.comment("Enable crystal button").define("enableCrystalButton",
                                        true);
                        X_CRYSTAL_BUTTON = builder.comment("X position of crystal button").defineInRange(
                                        "xCrystalButton", 0, -750,
                                        750);
                        Y_CRYSTAL_BUTTON = builder.comment("Y position of crystal button").defineInRange(
                                        "yCrystalButton", 0, -750,
                                        750);
                        X_CRYSTAL_BUTTON_CREATIVE = builder.comment("X position of crystal button in creative mode")
                                        .defineInRange("xCrystalButtonCreative", 0, -750, 750);
                        Y_CRYSTAL_BUTTON_CREATIVE = builder.comment("Y position of crystal button in creative mode")
                                        .defineInRange("yCrystalButtonCreative", 0, -750, 750);
                        builder.pop();

                        SPEC = builder.build();
                }
        }

        private static void bakeConfig() {
                xCollectCoins = Config.X_COLLECT_COINS.get();
                xCollectCoinsCreative = Config.X_COLLECT_COINS_CREATIVE.get();
                yCollectCoins = Config.Y_COLLECT_COINS.get();
                yCollectCoinsCreative = Config.Y_COLLECT_COINS_CREATIVE.get();
                enableConvertButtons = Config.ENABLE_CONVERT_BUTTONS.get();

                enableSilverButton = Config.ENABLE_SILVER_BUTTON.get();
                xSilverButton = Config.X_SILVER_BUTTON.get();
                xSilverButtonCreative = Config.X_SILVER_BUTTON_CREATIVE.get();
                ySilverButton = Config.Y_SILVER_BUTTON.get();
                ySilverButtonCreative = Config.Y_SILVER_BUTTON_CREATIVE.get();

                enableGoldButton = Config.ENABLE_GOLD_BUTTON.get();
                xGoldButton = Config.X_GOLD_BUTTON.get();
                xGoldButtonCreative = Config.X_GOLD_BUTTON_CREATIVE.get();
                yGoldButton = Config.Y_GOLD_BUTTON.get();
                yGoldButtonCreative = Config.Y_GOLD_BUTTON_CREATIVE.get();

                enableCrystalButton = Config.ENABLE_CRYSTAL_BUTTON.get();
                xCrystalButton = Config.X_CRYSTAL_BUTTON.get();
                xCrystalButtonCreative = Config.X_CRYSTAL_BUTTON_CREATIVE.get();
                yCrystalButton = Config.Y_CRYSTAL_BUTTON.get();
                yCrystalButtonCreative = Config.Y_CRYSTAL_BUTTON_CREATIVE.get();
        }

        @SubscribeEvent
        public static void onLoad(final ModConfigEvent event) {
                if (event.getConfig().getType() == ModConfig.Type.CLIENT
                                && event.getConfig().getSpec() == Config.SPEC) {
                        bakeConfig();
                }
        }
}
