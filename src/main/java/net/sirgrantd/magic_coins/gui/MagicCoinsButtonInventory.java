package net.sirgrantd.magic_coins.gui;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;

import top.theillusivec4.curios.api.client.ICuriosScreen;

import net.sirgrantd.magic_coins.config.ClientConfig;
import net.sirgrantd.magic_coins.gui.components.MagicButton;
import net.sirgrantd.magic_coins.gui.handlers.MagicCoinsButtonAction;

public class MagicCoinsButtonInventory {
    @SubscribeEvent
    public void onInventoryGuiInit(ScreenEvent.Init.Post event) {
        Screen screen = event.getScreen();

        if (screen instanceof InventoryScreen || screen instanceof CreativeModeInventoryScreen
                || screen instanceof ICuriosScreen) {
            AbstractContainerScreen<?> gui = (AbstractContainerScreen<?>) screen;
            boolean isCreative = screen instanceof CreativeModeInventoryScreen;
            boolean isCurios = screen instanceof ICuriosScreen;

            // Collect Coins Button
            if (ClientConfig.enableConvertButtons) {
                int xOffsetCollectCoins = (isCreative ? 2 : 2) + ClientConfig.xCollectCoins;
                int yOffsetCollectCoins = (isCreative ? -74 : -24) + ClientConfig.yCollectCoins;
                MagicButton collectCoinsButton = new MagicButton(gui, xOffsetCollectCoins, yOffsetCollectCoins, 0, 0,
                        18, 20, MagicButton.COLLECT_COINS_ICON, MagicCoinsButtonAction.COLLECT_COINS, null, null);
                event.addListener(collectCoinsButton);
            }

            // Silver Button
            if (ClientConfig.enableSilverButton) {
                int xOffsetSilver = (isCreative ? 127 : 77) + ClientConfig.xSilverButton;
                int yOffsetSilver = (isCreative ? 5 : 7) + ClientConfig.ySilverButton;
                MagicButton silverButton = new MagicButton(gui, xOffsetSilver, yOffsetSilver, 0, 0, 13, 13,
                        MagicButton.SILVER_ICON, MagicCoinsButtonAction.WITHDRAW_SILVER, null, null);
                event.addListener(silverButton);
            }

            // Gold Button
            if (ClientConfig.enableGoldButton) {
                int xOffsetGold = (isCreative ? 127 : 77) + ClientConfig.xGoldButton;
                int yOffsetGold = (isCreative ? 21 : 23) + ClientConfig.yGoldButton;
                MagicButton goldButton = new MagicButton(gui, xOffsetGold, yOffsetGold, 0, 0, 13, 13,
                        MagicButton.GOLD_ICON, MagicCoinsButtonAction.WITHDRAW_GOLD, null, null);
                event.addListener(goldButton);
            }

            // Crystal Button
            if (ClientConfig.enableCrystalButton) {
                int xOffsetCrystal = (isCreative ? 127 : 77) + ClientConfig.xCrystalButton;
                int yOffsetCrystal = (isCreative ? 37 : 39) + ClientConfig.yCrystalButton;
                MagicButton crystalButton = new MagicButton(gui, xOffsetCrystal, yOffsetCrystal, 0, 0, 13, 13,
                        MagicButton.CRYSTAL_ICON, MagicCoinsButtonAction.WITHDRAW_CRYSTAL, null, null);
                event.addListener(crystalButton);
            }

            if (!isCurios && !isCreative) {
                // Silver for Gold Button
                if (ClientConfig.enableSilverForGoldButton) {
                    int xOffsetSilverForGold = -30 + ClientConfig.xSilverForGoldButton;
                    int yOffsetSilverForGold = 7 + ClientConfig.ySilverForGoldButton;
                    MagicButton silverForGoldButton = new MagicButton(gui, xOffsetSilverForGold, yOffsetSilverForGold,
                            0, 0, 26, 13, MagicButton.SILVER_FOR_GOLD_ICON,
                            MagicCoinsButtonAction.CONVERT_SILVER_FOR_GOLD, null, null);
                    event.addListener(silverForGoldButton);
                }

                // Gold for Silver Button
                if (ClientConfig.enableGoldForSilverButton) {
                    int xOffsetGoldForSilver = -30 + ClientConfig.xGoldForSilverButton;
                    int yOffsetGoldForSilver = 23 + ClientConfig.yGoldForSilverButton;
                    MagicButton goldForSilverButton = new MagicButton(gui, xOffsetGoldForSilver, yOffsetGoldForSilver,
                            0, 0, 26, 13, MagicButton.GOLD_FOR_SILVER_ICON,
                            MagicCoinsButtonAction.CONVERT_GOLD_FOR_SILVER, null, null);
                    event.addListener(goldForSilverButton);
                }

                // Gold for Crystal Button
                if (ClientConfig.enableGoldForCrystalButton) {
                    int xOffsetGoldForCrystal = -30 + ClientConfig.xGoldForCrystalButton;
                    int yOffsetGoldForCrystal = 39 + ClientConfig.yGoldForCrystalButton;
                    MagicButton goldForCrystalButton = new MagicButton(gui, xOffsetGoldForCrystal,
                            yOffsetGoldForCrystal, 0, 0, 26, 13, MagicButton.GOLD_FOR_CRYSTAL_ICON,
                            MagicCoinsButtonAction.CONVERT_GOLD_FOR_CRYSTAL, null, null);
                    event.addListener(goldForCrystalButton);
                }

                // Crystal for Gold Button
                if (ClientConfig.enableCrystalForGoldButton) {
                    int xOffsetCrystalForGold = -30 + ClientConfig.xCrystalForGoldButton;
                    int yOffsetCrystalForGold = 55 + ClientConfig.yCrystalForGoldButton;
                    MagicButton crystalForGoldButton = new MagicButton(gui, xOffsetCrystalForGold,
                            yOffsetCrystalForGold, 0, 0, 26, 13, MagicButton.CRYSTAL_FOR_GOLD_ICON,
                            MagicCoinsButtonAction.CONVERT_CRYSTAL_FOR_GOLD, null, null);
                    event.addListener(crystalForGoldButton);
                }
            }
        }
    }
}