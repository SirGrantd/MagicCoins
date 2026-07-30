package net.sirgrantd.magic_coins.gui.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

import net.sirgrantd.magic_coins.MagicCoinsMod;

public class HandlerButton {

    private final MagicCoinsButtonAction action;

    public HandlerButton(MagicCoinsButtonAction action) {
        this.action = action;
    }

    public void ExecuteHandlerButton() {
        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        try {
            action.sendToServer(player);
        } catch (Exception e) {
            MagicCoinsMod.LOGGER.info(e.getMessage());
        }
    }
}