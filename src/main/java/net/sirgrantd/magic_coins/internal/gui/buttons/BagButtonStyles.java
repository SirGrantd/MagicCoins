package net.sirgrantd.magic_coins.internal.gui.buttons;

import java.util.function.Supplier;

import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.sirgrantd.magic_coins.MagicCoinsMod;
import net.sirgrantd.magic_coins.internal.gui.font.MouseIcon;

public class BagButtonStyles {

    public static final WidgetSprites SPRITES = new WidgetSprites(
            Identifier.fromNamespaceAndPath(MagicCoinsMod.MOD_ID, "button_collect_coins"),
            Identifier.fromNamespaceAndPath(MagicCoinsMod.MOD_ID, "button_collect_coins_highlighted"));

    public static Supplier<Component> bagButtonTooltip = () -> Component
            .translatable("tooltip.magic_coins.button.bag_button");

    public static Supplier<Component> bagButtonTooltipDetails = () -> {
        Component line1 = Component.translatable("tooltip.magic_coins.button.bag_button.details_line1",
                MouseIcon.LEFT_CLICK_ICON);
        Component line2 = Component.translatable("tooltip.magic_coins.button.bag_button.details_line2",
                MouseIcon.RIGHT_CLICK_ICON);
        Component line3 = Component.translatable("tooltip.magic_coins.button.bag_button.details_line3",
                MouseIcon.LEFT_CLICK_ICON, MouseIcon.RIGHT_CLICK_ICON);

        Component lineBreak = Component.literal("\n");

        return Component.empty().append(line1).append(lineBreak).append(line2).append(lineBreak).append(line3);
    };

}
