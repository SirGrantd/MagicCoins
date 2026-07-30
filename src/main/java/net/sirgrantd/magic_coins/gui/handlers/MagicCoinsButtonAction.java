package net.sirgrantd.magic_coins.gui.handlers;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

import net.neoforged.neoforge.network.PacketDistributor;
import net.sirgrantd.magic_coins.gui.buttons.ConvertCrystalForGold;
import net.sirgrantd.magic_coins.gui.buttons.ConvertGoldForCrystal;
import net.sirgrantd.magic_coins.gui.buttons.ConvertGoldForSilver;
import net.sirgrantd.magic_coins.gui.buttons.ConvertSilverForGold;
import net.sirgrantd.magic_coins.gui.buttons.DepositAllCoinsFromInventory;
import net.sirgrantd.magic_coins.gui.buttons.WithdrawCrystalCoin;
import net.sirgrantd.magic_coins.gui.buttons.WithdrawGoldCoin;
import net.sirgrantd.magic_coins.gui.buttons.WithdrawSilverCoin;

public enum MagicCoinsButtonAction {

    COLLECT_COINS(DepositAllCoinsFromInventory::new),
    WITHDRAW_SILVER(WithdrawSilverCoin::new),
    WITHDRAW_GOLD(WithdrawGoldCoin::new),
    WITHDRAW_CRYSTAL(WithdrawCrystalCoin::new),

    CONVERT_SILVER_FOR_GOLD(ConvertSilverForGold::new),
    CONVERT_GOLD_FOR_CRYSTAL(ConvertGoldForCrystal::new),
    CONVERT_GOLD_FOR_SILVER(ConvertGoldForSilver::new),
    CONVERT_CRYSTAL_FOR_GOLD(ConvertCrystalForGold::new);

    @FunctionalInterface
    public interface PacketFactory {
        CustomPacketPayload create(int x, int y, int z);
    }

    private final PacketFactory packetFactory;

    MagicCoinsButtonAction(PacketFactory packetFactory) {
        this.packetFactory = packetFactory;
    }

    public void sendToServer(Player player) {
        if (player == null) return;

        var pos = player.blockPosition();
        PacketDistributor.sendToServer(packetFactory.create(pos.getX(), pos.getY(), pos.getZ()));
    }
}