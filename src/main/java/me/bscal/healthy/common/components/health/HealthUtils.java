package me.bscal.healthy.common.components.health;

import me.bscal.healthy.common.mixin.HungerManagerAccessor;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.TranslatableText;

public final class HealthUtils
{

	/**
	 * Used for Healthy. Converts food, saturation -> healthy and possibly absorption. Does not check
	 * client/server side.
	 *
	 * @param player  - Player
	 * @param foodMod - Amount 1 food heals. Default 1. 0 for none
	 * @param satMod  - Amount 1 saturation heals. Default 1, 0 for none.
	 * @return If player was healed.
	 */
	public static boolean FoodToHealth(PlayerEntity player, float foodMod, float satMod,
			boolean applyAbsorption)
	{
		HungerManager hm = player.getHungerManager();
		HungerManagerAccessor accessor = (HungerManagerAccessor) hm;

		int food = hm.getFoodLevel();
		float saturation = hm.getSaturationLevel();
		float missingHP = player.getMaxHealth() - player.getHealth();

		if (missingHP <= 0)
			return false;

		// If the player has saturation apply that.
		if (saturation > 0)
		{
			// Heal clamps the value for us.
			player.heal(saturation * satMod);
			accessor.SetFoodSaturationLevel(0);
		}

		float heal = 0;
		for (; food > 1; food--)
		{
			if (player.getHealth() + heal >= player.getMaxHealth())
				break;
			heal += foodMod;
		}
		player.heal(heal);

		// If food is 3 or more
		if (applyAbsorption && food > 2)
		{
			player.setAbsorptionAmount(player.getAbsorptionAmount() + 2);
			food -= 2;
			player.sendMessage(new TranslatableText("msg.healthy.well.rested"), false);
		}
		hm.setFoodLevel(food);

		return true;
	}

	/**
	 * Used for Healthy. Instead of converting all food it only converts 1 saturation/food.
	 * Converts food, saturation -> healthy and possibly absorption. Does not check
	 * client/server side.
	 *
	 * @param player  - Player
	 * @param foodMod - Amount 1 food heals. Default 1. 0 for none
	 * @param satMod  - Amount 1 saturation heals. Default 1, 0 for none.
	 * @return If player was healed.
	 */
	public static boolean FoodToHealthTick(PlayerEntity player, float foodMod, float satMod,
			boolean applyAbsorption)
	{
		HungerManager hm = player.getHungerManager();
		HungerManagerAccessor accessor = (HungerManagerAccessor) hm;

		int food = hm.getFoodLevel();
		float saturation = hm.getSaturationLevel();
		float missingHP = player.getMaxHealth() - player.getHealth();

		if (missingHP == 0)
			return false;

		// If the player has saturation apply that.
		if (saturation > 0)
		{
			// Heal clamps the value for us.
			player.heal(satMod);
			accessor.SetFoodSaturationLevel(saturation - 1);
			return true;
		}
		else if (food > 3)
		{
			if (applyAbsorption && player.getAbsorptionAmount() < 2)
			{
				player.setAbsorptionAmount(player.getAbsorptionAmount() + 1);
				food -= 1;
				player.sendMessage(new TranslatableText("msg.healthy.well.rested"), false);
			}
			else
			{
				player.heal(foodMod);
				hm.setFoodLevel(food - 1);
			}
			return true;
		}
		return false;
	}
}
