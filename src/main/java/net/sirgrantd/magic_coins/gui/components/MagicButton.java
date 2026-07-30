package net.sirgrantd.magic_coins.gui.components;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import net.sirgrantd.magic_coins.MagicCoinsMod;
import net.sirgrantd.magic_coins.init.SoundsInit;
import net.sirgrantd.magic_coins.gui.handlers.HandlerButton;
import net.sirgrantd.magic_coins.gui.handlers.MagicCoinsButtonAction;

@OnlyIn(Dist.CLIENT)
public class MagicButton extends ImageButton {

    private final AbstractContainerScreen<?> parentGui;
    private final int xOffset;
    private final int yOffset;

    private final MagicCoinsButtonAction leftClickAction;
    private final MagicCoinsButtonAction rightClickAction;

    // Button sprites
    public static final WidgetSprites COLLECT_COINS_ICON = new WidgetSprites(
            ResourceLocation.fromNamespaceAndPath(MagicCoinsMod.MOD_ID, "button_collect_coins"),
            ResourceLocation.fromNamespaceAndPath(MagicCoinsMod.MOD_ID, "button_collect_coins_highlighted")
    );

    public static final WidgetSprites SILVER_ICON = new WidgetSprites(
            ResourceLocation.fromNamespaceAndPath(MagicCoinsMod.MOD_ID, "silver_button"),
            ResourceLocation.fromNamespaceAndPath(MagicCoinsMod.MOD_ID, "silver_button_highlighted")
    );

    public static final WidgetSprites GOLD_ICON = new WidgetSprites(
            ResourceLocation.fromNamespaceAndPath(MagicCoinsMod.MOD_ID, "gold_button"),
            ResourceLocation.fromNamespaceAndPath(MagicCoinsMod.MOD_ID, "gold_button_highlighted")
    );

    public static final WidgetSprites CRYSTAL_ICON = new WidgetSprites(
            ResourceLocation.fromNamespaceAndPath(MagicCoinsMod.MOD_ID, "crystal_button"),
            ResourceLocation.fromNamespaceAndPath(MagicCoinsMod.MOD_ID, "crystal_button_highlighted")
    );

    // Convert buttons
    public static final WidgetSprites SILVER_FOR_GOLD_ICON = new WidgetSprites(
            ResourceLocation.fromNamespaceAndPath(MagicCoinsMod.MOD_ID, "silver_for_gold_button"),
            ResourceLocation.fromNamespaceAndPath(MagicCoinsMod.MOD_ID, "silver_for_gold_button_highlighted")
    );

    public static final WidgetSprites GOLD_FOR_SILVER_ICON = new WidgetSprites(
            ResourceLocation.fromNamespaceAndPath(MagicCoinsMod.MOD_ID, "gold_for_silver_button"),
            ResourceLocation.fromNamespaceAndPath(MagicCoinsMod.MOD_ID, "gold_for_silver_button_highlighted")
    );

    public static final WidgetSprites GOLD_FOR_CRYSTAL_ICON = new WidgetSprites(
            ResourceLocation.fromNamespaceAndPath(MagicCoinsMod.MOD_ID, "gold_for_crystal_button"),
            ResourceLocation.fromNamespaceAndPath(MagicCoinsMod.MOD_ID, "gold_for_crystal_button_highlighted")
    );

    public static final WidgetSprites CRYSTAL_FOR_GOLD_ICON = new WidgetSprites(
            ResourceLocation.fromNamespaceAndPath(MagicCoinsMod.MOD_ID, "crystal_for_gold_button"),
            ResourceLocation.fromNamespaceAndPath(MagicCoinsMod.MOD_ID, "crystal_for_gold_button_highlighted")
    );

    public MagicButton(
            AbstractContainerScreen<?> parentGui,
            int xOffset,
            int yOffset,
            int xIn,
            int yIn,
            int widthIn,
            int heightIn,
            WidgetSprites sprites,
            MagicCoinsButtonAction leftClickAction,
            MagicCoinsButtonAction rightClickAction,
            Component tooltipText
    ) {
        super(xIn, yIn, widthIn, heightIn, sprites, (btn) -> {});

        this.parentGui = parentGui;
        this.xOffset = xOffset;
        this.yOffset = yOffset;

        this.leftClickAction = leftClickAction;
        this.rightClickAction = rightClickAction;

        if (tooltipText != null) {
            this.setTooltip(Tooltip.create(tooltipText));
        }
    }

    @Override
    public void playDownSound(SoundManager soundHandler) {
        soundHandler.play(SimpleSoundInstance.forUI(SoundsInit.MAGIC_BAG_COLLECT_COINS.get(), 1.25F));
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.setX(parentGui.getGuiLeft() + xOffset);
        this.setY(parentGui.getGuiTop() + yOffset);

        if (parentGui instanceof CreativeModeInventoryScreen creativeScreen) {
            boolean isInventoryTab = creativeScreen.isInventoryOpen();
            this.active = isInventoryTab;

            if (!isInventoryTab) {
                return;
            }
        }

        super.renderWidget(guiGraphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!this.active || !this.visible) return false;
        if (!this.isMouseOver(mouseX, mouseY)) return false;

        MagicCoinsButtonAction actionToRun = switch (button) {
            case 0 -> leftClickAction;
            case 1 -> rightClickAction;
            default -> null;
        };

        if (actionToRun != null) {
            this.playDownSound(parentGui.getMinecraft().getSoundManager());

            HandlerButton handler = new HandlerButton(actionToRun);
            handler.ExecuteHandlerButton();
        }

        return true;
    }
}