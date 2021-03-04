package me.bscal.healthy.common.events;

import me.bscal.healthy.Healthy;
import me.bscal.healthy.common.components.health.HealthProvider;
import me.bscal.healthy.common.components.health.IHealthComponent;
import me.bscal.healthy.common.events.callbacks.PlayerTickCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import org.apache.logging.log4j.Level;

public class PlayerTickListener implements PlayerTickCallback
{
	@Override
	public ActionResult tick(PlayerEntity player)
	{
		if (!player.world.isClient && player.getServer() != null)
		{
			IHealthComponent health = HealthProvider.HEALTH.get(player);

			if (health.CanConsume(player.getServer().getTicks()))
			{
				player.heal(health.ConsumeHealing());

				Healthy.LOGGER.log(Level.INFO,"Consuming health.");
			}
		}
		return ActionResult.PASS;
	}
}
