package me.bscal.healthy.common.events;

import me.bscal.healthy.common.components.health.HealthUtils;
import me.bscal.healthy.common.events.callbacks.PlayerSleepCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

public class PlayerSleepListener implements PlayerSleepCallback
{

	private static final float FOOD_WEIGHT = 1.0f;
	private static final float SAT_WEIGHT = 1.0f;

	@Override
	public ActionResult onSleep(PlayerEntity player, World world)
	{
		if (!world.isClient)
		{
			HealthUtils.FoodToHealth(player, FOOD_WEIGHT, SAT_WEIGHT, true);
		}
		return ActionResult.PASS;
	}
}
