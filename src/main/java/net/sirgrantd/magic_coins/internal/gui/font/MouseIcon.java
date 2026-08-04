package net.sirgrantd.magic_coins.internal.gui.font;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.network.chat.FontDescription;

public class MouseIcon {

        private static final Style ICON_STYLE = Style.EMPTY
                        .withFont(new FontDescription.Resource(Identifier.parse("magic_coins:icons")));

        public static final MutableComponent LEFT_CLICK_ICON = Component.literal("\uE001").withStyle(ICON_STYLE)
                        .withStyle(ChatFormatting.WHITE);
        public static final MutableComponent RIGHT_CLICK_ICON = Component.literal("\uE002").withStyle(ICON_STYLE)
                        .withStyle(ChatFormatting.WHITE);

        public static final MutableComponent SILVER_COIN_ICON = Component.literal("\uE003").withStyle(ICON_STYLE);
        public static final MutableComponent GOLD_COIN_ICON = Component.literal("\uE004").withStyle(ICON_STYLE);
        public static final MutableComponent CRYSTAL_COIN_ICON = Component.literal("\uE005").withStyle(ICON_STYLE);

}
