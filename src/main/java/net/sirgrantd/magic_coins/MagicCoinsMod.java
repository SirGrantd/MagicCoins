package net.sirgrantd.magic_coins;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.sirgrantd.celesthyd.api.CelesthydApi;
import net.sirgrantd.magic_coins.internal.config.ClientConfig;
import net.sirgrantd.magic_coins.internal.config.LootConfigManager;
import net.sirgrantd.magic_coins.internal.config.ServerConfig;
import net.sirgrantd.magic_coins.internal.init.MagicCoinsItems;
import net.sirgrantd.magic_coins.internal.init.MagicCoinsLootsConditions;
import net.sirgrantd.magic_coins.internal.init.MagicCoinsSounds;
import net.sirgrantd.magic_coins.internal.init.MagicCoinsTab;
import net.sirgrantd.magic_coins.internal.network.payload.SyncServerConfigS2C;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;

@Mod(MagicCoinsMod.MOD_ID)
public class MagicCoinsMod {
	public static final Logger LOGGER = LogManager.getLogger(MagicCoinsMod.class);
	public static final String MOD_ID = "magic_coins";

	public MagicCoinsMod(IEventBus eventBus, ModContainer modContainer) {
		eventBus.addListener(MagicCoinsMod::onCommonSetupEvent);

		LootConfigManager.loadConfigs();

		MagicCoinsTab.REGISTRY.register(eventBus);
		MagicCoinsItems.register(eventBus);
		MagicCoinsSounds.register(eventBus);
		MagicCoinsLootsConditions.register(eventBus);

		modContainer.registerConfig(ModConfig.Type.CLIENT, ClientConfig.Config.SPEC,
				String.format("%s-client.toml", MOD_ID));
		modContainer.registerConfig(ModConfig.Type.SERVER, ServerConfig.Config.SPEC,
				String.format("%s-server.toml", MOD_ID));
	}

	public static void onCommonSetupEvent(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			CelesthydApi.registerConfigSync(() -> new SyncServerConfigS2C(ServerConfig.silverCoinsValue,
					ServerConfig.goldCoinsValue, ServerConfig.crystalCoinsValue));
		});
	}
}
