package net.sirgrantd.magic_coins.internal.gui;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.sirgrantd.celesthyd.api.gui.CelesthydButton;
import net.sirgrantd.magic_coins.internal.config.ClientConfig;
import net.sirgrantd.magic_coins.internal.gui.buttons.BagButtonStyles;
import net.sirgrantd.magic_coins.internal.gui.buttons.CrystalButtonStyles;
import net.sirgrantd.magic_coins.internal.gui.buttons.GoldButtonStyles;
import net.sirgrantd.magic_coins.internal.gui.buttons.SilverButtonStyles;
import net.sirgrantd.magic_coins.internal.init.MagicCoinsSounds;

@EventBusSubscriber({ Dist.CLIENT })
public class MagicInventory {

        @SubscribeEvent
        public static void OnInventoryGuiInit(ScreenEvent.Init.Post event) {
                Screen screen = event.getScreen();

                boolean isInventory = screen instanceof InventoryScreen;
                boolean isCreativeInventory = screen instanceof CreativeModeInventoryScreen;
                boolean isCurios = false;

                if (ModList.get().isLoaded("curios")) {
                        try {
                                Class<?> curiosScreenClass = Class
                                                .forName("top.theillusivec4.curios.api.client.ICuriosScreen");
                                isCurios = curiosScreenClass.isInstance(screen);

                        } catch (ClassNotFoundException ignored) {
                        }
                }

                if (isInventory || isCreativeInventory || isCurios) {

                        AbstractContainerScreen<?> gui = (AbstractContainerScreen<?>) screen;

                        int xInvPos = gui.getLeftPos();
                        int yInvPos = gui.getTopPos();

                        if (ClientConfig.enableConvertButtons) {
                                int xOffsetCollectCoins = (isCreativeInventory ? 2 + ClientConfig.xCollectCoinsCreative
                                                : 2 + ClientConfig.xCollectCoins);
                                int yOffsetCollectCoins = (isCreativeInventory
                                                ? 167 + ClientConfig.yCollectCoinsCreative
                                                : -24 + ClientConfig.yCollectCoins);

                                CelesthydButton bagButton = new CelesthydButton(
                                                gui,
                                                xOffsetCollectCoins,
                                                yOffsetCollectCoins,
                                                xInvPos,
                                                yInvPos,
                                                18,
                                                20,
                                                BagButtonStyles.SPRITES,
                                                EMagicCoinsButton.BAG_BUTTON_LEFT_CLICK,
                                                EMagicCoinsButton.BAG_BUTTON_RIGHT_CLICK,
                                                () -> MagicCoinsSounds.MAGIC_BAG_COLLECT_COINS.get(),
                                                BagButtonStyles.bagButtonTooltip,
                                                BagButtonStyles.bagButtonTooltipDetails);
                                event.addListener(bagButton);
                        }

                        if (ClientConfig.enableSilverButton) {
                                int xOffsetSilver = (isCreativeInventory ? 100 + ClientConfig.xSilverButtonCreative
                                                : 77 + ClientConfig.xSilverButton);
                                int yOffsetSilver = (isCreativeInventory ? 165 + ClientConfig.ySilverButtonCreative
                                                : 7 + ClientConfig.ySilverButton);

                                CelesthydButton silverButton = new CelesthydButton(
                                                gui,
                                                xOffsetSilver,
                                                yOffsetSilver,
                                                xInvPos,
                                                yInvPos,
                                                13,
                                                13,
                                                SilverButtonStyles.SPRITES,
                                                EMagicCoinsButton.SILVER_BUTTON_LEFT_CLICK,
                                                EMagicCoinsButton.SILVER_BUTTON_RIGHT_CLICK,
                                                () -> MagicCoinsSounds.MAGIC_BAG_COLLECT_COINS.get(),
                                                SilverButtonStyles.silverButtonTooltip,
                                                SilverButtonStyles.silverButtonTooltipDetails);
                                event.addListener(silverButton);
                        }

                        if (ClientConfig.enableGoldButton) {
                                int xOffsetGold = (isCreativeInventory ? 120 + ClientConfig.xGoldButtonCreative
                                                : 77 + ClientConfig.xGoldButton);
                                int yOffsetGold = (isCreativeInventory ? 165 + ClientConfig.yGoldButtonCreative
                                                : 23 + ClientConfig.yGoldButton);

                                CelesthydButton goldButton = new CelesthydButton(
                                                gui,
                                                xOffsetGold,
                                                yOffsetGold,
                                                xInvPos,
                                                yInvPos,
                                                13,
                                                13,
                                                GoldButtonStyles.SPRITES,
                                                EMagicCoinsButton.GOLD_BUTTON_LEFT_CLICK,
                                                EMagicCoinsButton.GOLD_BUTTON_RIGHT_CLICK,
                                                () -> MagicCoinsSounds.MAGIC_BAG_COLLECT_COINS.get(),
                                                GoldButtonStyles.goldButtonTooltip,
                                                GoldButtonStyles.goldButtonTooltipDetails);
                                event.addListener(goldButton);
                        }

                        if (ClientConfig.enableCrystalButton) {
                                int xOffsetCrystal = (isCreativeInventory ? 140 + ClientConfig.xCrystalButtonCreative
                                                : 77 + ClientConfig.xCrystalButton);
                                int yOffsetCrystal = (isCreativeInventory ? 165 + ClientConfig.yCrystalButtonCreative
                                                : 39 + ClientConfig.yCrystalButton);

                                CelesthydButton crystalButton = new CelesthydButton(
                                                gui,
                                                xOffsetCrystal,
                                                yOffsetCrystal,
                                                xInvPos,
                                                yInvPos,
                                                13,
                                                13,
                                                CrystalButtonStyles.SPRITES,
                                                EMagicCoinsButton.CRYSTAL_BUTTON_LEFT_CLICK,
                                                EMagicCoinsButton.CRYSTAL_BUTTON_RIGHT_CLICK,
                                                () -> MagicCoinsSounds.MAGIC_BAG_COLLECT_COINS.get(),
                                                CrystalButtonStyles.crystalButtonTooltip,
                                                CrystalButtonStyles.crystalButtonTooltipDetails);
                                event.addListener(crystalButton);
                        }

                }

        }
}
