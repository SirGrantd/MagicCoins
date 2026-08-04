package net.sirgrantd.magic_coins.internal.gui;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.sirgrantd.celesthyd.api.gui.CelesthydButtonAction;
import net.sirgrantd.magic_coins.internal.gui.buttons.BagButtonActions;
import net.sirgrantd.magic_coins.internal.gui.buttons.CrystalButtonActions;
import net.sirgrantd.magic_coins.internal.gui.buttons.GoldButtonActions;
import net.sirgrantd.magic_coins.internal.gui.buttons.SilverButtonActions;
import net.sirgrantd.magic_coins.internal.network.payload.MagicButtonPayload;

public enum EMagicCoinsButton implements CelesthydButtonAction {

    BAG_BUTTON_LEFT_CLICK(BagButtonActions::executeLeftClick),
    BAG_BUTTON_RIGHT_CLICK(BagButtonActions::executeRightClick),

    SILVER_BUTTON_LEFT_CLICK(SilverButtonActions::executeLeftClick),
    SILVER_BUTTON_RIGHT_CLICK(SilverButtonActions::executeRightClick),

    GOLD_BUTTON_LEFT_CLICK(GoldButtonActions::executeLeftClick),
    GOLD_BUTTON_RIGHT_CLICK(GoldButtonActions::executeRightClick),

    CRYSTAL_BUTTON_LEFT_CLICK(CrystalButtonActions::executeLeftClick),
    CRYSTAL_BUTTON_RIGHT_CLICK(CrystalButtonActions::executeRightClick);

    @FunctionalInterface
    public interface ServerAction {
        void execute(Player player, boolean isShiftDown);
    }

    private final ServerAction serverAction;

    EMagicCoinsButton(ServerAction serverAction) {
        this.serverAction = serverAction;
    }

    @Override
    public CustomPacketPayload createPacket(int x, int y, int z, boolean isShiftDown) {
        return new MagicButtonPayload(this.name(), x, y, z, isShiftDown);
    }

    public void executeOnServer(Player player, boolean isShiftDown) {
        this.serverAction.execute(player, isShiftDown);
    }
}
