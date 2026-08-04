package net.sirgrantd.magic_coins.internal.gui.buttons;

import java.util.function.Supplier;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.sirgrantd.celesthyd.api.CelesthydApi;
import net.sirgrantd.magic_coins.MagicCoinsMod;
import net.sirgrantd.magic_coins.api.MagicCoinsApi;
import net.sirgrantd.magic_coins.internal.gui.font.MouseIcon;
import net.sirgrantd.magic_coins.internal.init.MagicCoinsItems;

public class GoldButtonStyles {

    public static final WidgetSprites SPRITES = new WidgetSprites(
            Identifier.fromNamespaceAndPath(MagicCoinsMod.MOD_ID, "gold_button"),
            Identifier.fromNamespaceAndPath(MagicCoinsMod.MOD_ID, "gold_button_highlighted"));

    public static Supplier<Component> goldButtonTooltip = () -> Component
            .translatable("tooltip.magic_coins.button.gold_button");

    public static Supplier<Component> goldButtonTooltipDetails = () -> {
        Player player = Minecraft.getInstance().player;

        int maxWithdraw = (int) Math.min(64, MagicCoinsApi.CoinsCount.countGoldCoinsFreeForInventory(player));

        if (maxWithdraw == 0) {
            maxWithdraw = 64;
        }

        String maxWithdrawFormatted = ChatFormatting.GRAY + String.valueOf(maxWithdraw) + "x";

        int maxDeposit = (int) Math.min(64,
                CelesthydApi.Inventory.countItems(player.getInventory(), MagicCoinsItems.GOLD_COIN.get()));

        if (maxDeposit == 0) {
            maxDeposit = 64;
        }

        String maxDepositFormatted = ChatFormatting.GRAY + String.valueOf(maxDeposit) + "x";

        Component line1 = Component
                .translatable("tooltip.magic_coins.button.gold_button.details_line1", MouseIcon.LEFT_CLICK_ICON,
                        MouseIcon.GOLD_COIN_ICON);
        Component line2 = Component
                .translatable("tooltip.magic_coins.button.gold_button.details_line2", MouseIcon.LEFT_CLICK_ICON,
                        maxWithdrawFormatted, MouseIcon.GOLD_COIN_ICON);
        Component line3 = Component
                .translatable("tooltip.magic_coins.button.gold_button.details_line3", MouseIcon.RIGHT_CLICK_ICON,
                        MouseIcon.GOLD_COIN_ICON);
        Component line4 = Component.translatable("tooltip.magic_coins.button.gold_button.details_line4",
                MouseIcon.RIGHT_CLICK_ICON, maxDepositFormatted, MouseIcon.GOLD_COIN_ICON);

        Component lineBreak = Component.literal("\n");

        return Component.empty().append(line1).append(lineBreak).append(line2).append(lineBreak).append(line3)
                .append(lineBreak).append(line4);
    };

}
