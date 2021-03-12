package me.bscal.healthy.common.events;

import me.bscal.healthy.common.events.callbacks.PlayerSleepCallback;
import me.bscal.healthy.common.mixin.HungerManagerAccessor;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.TranslatableText;
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
			HungerManager hm = player.getHungerManager();
			HungerManagerAccessor accessor = (HungerManagerAccessor) hm;

			int food = hm.getFoodLevel();
			float saturation = hm.getSaturationLevel();
			float missingHP = player.getMaxHealth() - player.getHealth();

			if (missingHP <= 0) return ActionResult.PASS;

			// If the player has saturation apply that.
			if (saturation > 0)
			{
				// Heal clamps the value for us.
				player.heal(saturation * SAT_WEIGHT);
				accessor.SetFoodSaturationLevel(0);
			}

			float heal = 0;
			for (; food > 0; food--)
			{
				if (player.getHealth() + heal >= player.getMaxHealth())
					break;
				heal += FOOD_WEIGHT;
			}
			player.heal(heal);

			if (food > 2)
			{
				player.setAbsorptionAmount(player.getAbsorptionAmount() + 2);
				food -= 2;
				player.sendMessage(new TranslatableText("msg.healthy.well.rested"), false);
			}
			hm.setFoodLevel(food);
		}
		return ActionResult.PASS;
	}
}
