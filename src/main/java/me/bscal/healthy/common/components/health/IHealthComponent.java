package me.bscal.healthy.common.components.health;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;

public interface IHealthComponent extends ComponentV3
{

	/**
	 * Adds a heal if possible
	 */
	void AddHealing(Heal heal);

	/**
	 * Returns true if the player can be healed. This is not relevant if trying to add a heal to the player.
	 */
	boolean CanConsume(int currentTickCount);

	/**
	 * Process and update healing.
	 */
	float ConsumeHealing();

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
