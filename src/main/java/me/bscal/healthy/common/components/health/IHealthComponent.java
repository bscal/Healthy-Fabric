package me.bscal.healthy.common.components.health;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import me.bscal.healthy.common.components.buff.IBuff;
import net.minecraft.entity.LivingEntity;

public interface IHealthComponent extends ComponentV3
{

	/**
	 * Adds a heal if possible
	 */
	void AddHealing(Heal heal);

	/**
	 * Process and update healing.
	 */
	void UpdateHealth(LivingEntity entity);

	/**
	 * Sets and overrides current healing.
	 */
	void SetHealing(Heal heal);

	/**
	 * Stops all healing.
	 */
	void StopHealing();

	/**
	 *	Returns Heal class containing all info on the current heal.
	 */
	Heal GetHeal();

	/**
	 * Returns true if a Heal can be added to the player.
	 */
	boolean CanReceiveHealing();


}
