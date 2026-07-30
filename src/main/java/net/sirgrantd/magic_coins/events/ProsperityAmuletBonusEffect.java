package net.sirgrantd.magic_coins.events;

import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.sirgrantd.sg_economy.api.EconomyEventProvider;
import net.sirgrantd.sg_economy.api.SGEconomyApi;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

@EventBusSubscriber
public class ProsperityAmuletBonusEffect {
	@SubscribeEvent
	public static void onPlayerAttacked(LivingDamageEvent.Post event) {
		Entity entity = event.getEntity();

		if (entity instanceof Player player) {
            handlePlayerAttacked(player);
        }
	}

	private static void handlePlayerAttacked(Player player) {
        EconomyEventProvider event = SGEconomyApi.get();

		boolean effectBonusActive = 
			event.getPercentageBalanceSaveOnDeath() == 100 &&
			!event.balanceLostOnDeath(player);
		
		float health = player.getHealth();
		float maxHealth = player.getMaxHealth();

		if (effectBonusActive && health <= maxHealth * 0.5) {
			player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 60, 0));
		}
	}
}
