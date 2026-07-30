package net.sirgrantd.magic_coins;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;

import net.minecraft.util.Tuple;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.List;
import java.util.Collection;
import java.util.ArrayList;

import net.sirgrantd.magic_coins.config.ClientConfig;
import net.sirgrantd.magic_coins.config.ServerConfig;
import net.sirgrantd.magic_coins.config.LootConfigManager;
import net.sirgrantd.magic_coins.init.ItemsInit;
import net.sirgrantd.magic_coins.init.LootInit;
import net.sirgrantd.magic_coins.init.SoundsInit;
import net.sirgrantd.magic_coins.init.TabsInit;
import net.sirgrantd.magic_coins.network.MagicNetwork;
import net.sirgrantd.magic_coins.network.payload.SyncServerConfigS2C;
import net.sirgrantd.magic_coins.gui.MagicCoinsButtonInventory;

@Mod("magic_coins")
public class MagicCoinsMod {
	public static final Logger LOGGER = LogManager.getLogger(MagicCoinsMod.class);
	public static final String MOD_ID = "magic_coins";

	public MagicCoinsMod(IEventBus modEventBus, ModContainer modContainer) {
		NeoForge.EVENT_BUS.register(MagicCoinsMod.class);
		modEventBus.addListener(MagicNetwork::registerNetworking);

		MagicNetwork.addNetworkMessage(
				SyncServerConfigS2C.TYPE,
				SyncServerConfigS2C.STREAM_CODEC,
				SyncServerConfigS2C::handle);

		TabsInit.REGISTRY.register(modEventBus);
		ItemsInit.REGISTRY.register(modEventBus);

		LootConfigManager.loadConfigs();
		LootInit.register(modEventBus);
		SoundsInit.register(modEventBus);

		modContainer.registerConfig(ModConfig.Type.CLIENT, ClientConfig.Config.SPEC,
				String.format("%s-client.toml", MOD_ID));
		modContainer.registerConfig(ModConfig.Type.SERVER, ServerConfig.Config.SPEC,
				String.format("%s-server.toml", MOD_ID));
	}

	@EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
	public static class ClientProxy {

		@SubscribeEvent
		public static void setupClient(FMLClientSetupEvent event) {
			NeoForge.EVENT_BUS.register(new MagicCoinsButtonInventory());
		}
	}

	private static final Collection<Tuple<Runnable, Integer>> workQueue = new ConcurrentLinkedQueue<>();

	public static void queueServerWork(int tick, Runnable action) {
		workQueue.add(new Tuple<>(action, tick));
	}

	@SubscribeEvent
	public static void tick(ServerTickEvent.Post event) {
		List<Tuple<Runnable, Integer>> actions = new ArrayList<>();
		workQueue.forEach(work -> {
			work.setB(work.getB() - 1);
			if (work.getB() == 0)
				actions.add(work);
		});
		actions.forEach(e -> e.getA().run());
		workQueue.removeAll(actions);
	}
}
