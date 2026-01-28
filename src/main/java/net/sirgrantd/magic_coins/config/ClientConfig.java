package net.sirgrantd.magic_coins.config;

import java.util.function.Supplier;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

import net.sirgrantd.magic_coins.MagicCoinsMod;

@EventBusSubscriber(modid = MagicCoinsMod.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientConfig {
    // Collect Coins Button
    public static int xCollectCoins;
    public static int yCollectCoins;
    public static boolean enableConvertButtons;

    // Buttons
    public static int xSilverButton, ySilverButton;
    public static boolean enableSilverButton;

    public static int xGoldButton, yGoldButton;
    public static boolean enableGoldButton;

    public static int xCrystalButton, yCrystalButton;
    public static boolean enableCrystalButton;

    public static boolean enableSilverForGoldButton;
    public static int xSilverForGoldButton, ySilverForGoldButton;

    public static boolean enableGoldForSilverButton;
    public static int xGoldForSilverButton, yGoldForSilverButton;

    public static boolean enableGoldForCrystalButton;
    public static int xGoldForCrystalButton, yGoldForCrystalButton;

    public static boolean enableCrystalForGoldButton;
    public static int xCrystalForGoldButton, yCrystalForGoldButton;

    public static class Config {
        public static ModConfigSpec SPEC;
        // Collect Coins Button
        public static final Supplier<Integer> X_COLLECT_COINS;
        public static final Supplier<Integer> Y_COLLECT_COINS;
        public static final Supplier<Boolean> ENABLE_CONVERT_BUTTONS;

        // Silver Button
        public static final Supplier<Boolean> ENABLE_SILVER_BUTTON;
        public static final Supplier<Integer> X_SILVER_BUTTON;
        public static final Supplier<Integer> Y_SILVER_BUTTON;

        // Gold Button
        public static final Supplier<Boolean> ENABLE_GOLD_BUTTON;
        public static final Supplier<Integer> X_GOLD_BUTTON;
        public static final Supplier<Integer> Y_GOLD_BUTTON;

        // Crystal Button
        public static final Supplier<Boolean> ENABLE_CRYSTAL_BUTTON;
        public static final Supplier<Integer> X_CRYSTAL_BUTTON;
        public static final Supplier<Integer> Y_CRYSTAL_BUTTON;

        // Silver for Gold Button
        public static final Supplier<Boolean> ENABLE_SILVER_FOR_GOLD_BUTTON;
        public static final Supplier<Integer> X_SILVER_FOR_GOLD_BUTTON;
        public static final Supplier<Integer> Y_SILVER_FOR_GOLD_BUTTON;

        // Gold for Silver Button
        public static final Supplier<Boolean> ENABLE_GOLD_FOR_SILVER_BUTTON;
        public static final Supplier<Integer> X_GOLD_FOR_SILVER_BUTTON;
        public static final Supplier<Integer> Y_GOLD_FOR_SILVER_BUTTON;

        // Gold for Crystal Button
        public static final Supplier<Boolean> ENABLE_GOLD_FOR_CRYSTAL_BUTTON;
        public static final Supplier<Integer> X_GOLD_FOR_CRYSTAL_BUTTON;
        public static final Supplier<Integer> Y_GOLD_FOR_CRYSTAL_BUTTON;

        // Crystal for Gold Button
        public static final Supplier<Boolean> ENABLE_CRYSTAL_FOR_GOLD_BUTTON;
        public static final Supplier<Integer> X_CRYSTAL_FOR_GOLD_BUTTON;
        public static final Supplier<Integer> Y_CRYSTAL_FOR_GOLD_BUTTON;

        static {
            ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

            // Collect Coins Button group           
            builder.push("COLLECT_COINS_BUTTON");
            X_COLLECT_COINS = builder.comment("X position for coin button").comment("Tips: 80 for align for right side").defineInRange("xCollectCoins", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
            Y_COLLECT_COINS = builder.comment("Y position for coin button").defineInRange("yCollectCoins", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
            ENABLE_CONVERT_BUTTONS = builder.comment("Enable convert buttons on collect coins button").define("enableConvertButtons", true);
            builder.pop();

            // Silver Button group
            builder.push("SILVER_BUTTON");
            ENABLE_SILVER_BUTTON = builder.comment("Enable silver button").define("enableSilverButton", true);
            X_SILVER_BUTTON = builder.comment("X position of silver button").defineInRange("xSilverButton", 0, -750, 750);
            Y_SILVER_BUTTON = builder.comment("Y position of silver button").defineInRange("ySilverButton", 0, -750, 750);
            builder.pop();

            // Gold Button group
            builder.push("GOLD_BUTTON");
            ENABLE_GOLD_BUTTON = builder.comment("Enable gold button").define("enableGoldButton", true);
            X_GOLD_BUTTON = builder.comment("X position of gold button").defineInRange("xGoldButton", 0, -750, 750);
            Y_GOLD_BUTTON = builder.comment("Y position of gold button").defineInRange("yGoldButton", 0, -750, 750);
            builder.pop();

            // Crystal Button group
            builder.push("CRYSTAL_BUTTON");
            ENABLE_CRYSTAL_BUTTON = builder.comment("Enable crystal button").define("enableCrystalButton", true);
            X_CRYSTAL_BUTTON = builder.comment("X position of crystal button").defineInRange("xCrystalButton", 0, -750, 750);
            Y_CRYSTAL_BUTTON = builder.comment("Y position of crystal button").defineInRange("yCrystalButton", 0, -750, 750);
            builder.pop();

            // Silver for Gold Button
            builder.push("SILVER_FOR_GOLD_BUTTON");
            ENABLE_SILVER_FOR_GOLD_BUTTON = builder.comment("Enable silver-for-gold button").define("enableSilverForGoldButton", false);
            X_SILVER_FOR_GOLD_BUTTON = builder.comment("X position").defineInRange("xSilverForGoldButton", 0, -750, 750);
            Y_SILVER_FOR_GOLD_BUTTON = builder.comment("Y position").defineInRange("ySilverForGoldButton", 0, -750, 750);
            builder.pop();

            // Gold for Silver Button
            builder.push("GOLD_FOR_SILVER_BUTTON");
            ENABLE_GOLD_FOR_SILVER_BUTTON = builder.comment("Enable gold-for-silver button").define("enableGoldForSilverButton", false);
            X_GOLD_FOR_SILVER_BUTTON = builder.comment("X position").defineInRange("xGoldForSilverButton", 0, -750, 750);
            Y_GOLD_FOR_SILVER_BUTTON = builder.comment("Y position").defineInRange("yGoldForSilverButton", 0, -750, 750);
            builder.pop();

            // Gold for Crystal Button
            builder.push("GOLD_FOR_CRYSTAL_BUTTON");
            ENABLE_GOLD_FOR_CRYSTAL_BUTTON = builder.comment("Enable gold-for-crystal button").define("enableGoldForCrystalButton", false);
            X_GOLD_FOR_CRYSTAL_BUTTON = builder.comment("X position").defineInRange("xGoldForCrystalButton", 0, -750, 750);
            Y_GOLD_FOR_CRYSTAL_BUTTON = builder.comment("Y position").defineInRange("yGoldForCrystalButton", 0, -750, 750);
            builder.pop();

            // Crystal for Gold Button
            builder.push("CRYSTAL_FOR_GOLD_BUTTON");
            ENABLE_CRYSTAL_FOR_GOLD_BUTTON = builder.comment("Enable crystal-for-gold button").define("enableCrystalForGoldButton", false);
            X_CRYSTAL_FOR_GOLD_BUTTON = builder.comment("X position").defineInRange("xCrystalForGoldButton", 0, -750, 750);
            Y_CRYSTAL_FOR_GOLD_BUTTON = builder.comment("Y position").defineInRange("yCrystalForGoldButton", 0, -750, 750);
            builder.pop();

            SPEC = builder.build();
        }
    }

    private static void bakeConfig() {
        xCollectCoins = Config.X_COLLECT_COINS.get();
        yCollectCoins = Config.Y_COLLECT_COINS.get();
        enableConvertButtons = Config.ENABLE_CONVERT_BUTTONS.get();

        enableSilverButton = Config.ENABLE_SILVER_BUTTON.get();
        xSilverButton = Config.X_SILVER_BUTTON.get();
        ySilverButton = Config.Y_SILVER_BUTTON.get();

        enableGoldButton = Config.ENABLE_GOLD_BUTTON.get();
        xGoldButton = Config.X_GOLD_BUTTON.get();
        yGoldButton = Config.Y_GOLD_BUTTON.get();

        enableCrystalButton = Config.ENABLE_CRYSTAL_BUTTON.get();
        xCrystalButton = Config.X_CRYSTAL_BUTTON.get();
        yCrystalButton = Config.Y_CRYSTAL_BUTTON.get();

        enableSilverForGoldButton = Config.ENABLE_SILVER_FOR_GOLD_BUTTON.get();
        xSilverForGoldButton = Config.X_SILVER_FOR_GOLD_BUTTON.get();
        ySilverForGoldButton = Config.Y_SILVER_FOR_GOLD_BUTTON.get();

        enableGoldForSilverButton = Config.ENABLE_GOLD_FOR_SILVER_BUTTON.get();
        xGoldForSilverButton = Config.X_GOLD_FOR_SILVER_BUTTON.get();
        yGoldForSilverButton = Config.Y_GOLD_FOR_SILVER_BUTTON.get();

        enableGoldForCrystalButton = Config.ENABLE_GOLD_FOR_CRYSTAL_BUTTON.get();
        xGoldForCrystalButton = Config.X_GOLD_FOR_CRYSTAL_BUTTON.get();
        yGoldForCrystalButton = Config.Y_GOLD_FOR_CRYSTAL_BUTTON.get();

        enableCrystalForGoldButton = Config.ENABLE_CRYSTAL_FOR_GOLD_BUTTON.get();
        xCrystalForGoldButton = Config.X_CRYSTAL_FOR_GOLD_BUTTON.get();
        yCrystalForGoldButton = Config.Y_CRYSTAL_FOR_GOLD_BUTTON.get();
    }

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getType() == ModConfig.Type.CLIENT && event.getConfig().getSpec() == Config.SPEC) {
            bakeConfig();
        }
    }
}