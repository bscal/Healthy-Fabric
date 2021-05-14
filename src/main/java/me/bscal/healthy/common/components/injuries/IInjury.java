package me.bscal.healthy.common.components.injuries;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public interface IInjury
{

	/**
	 * Creates a new instance of injury.
	 */
	IInjury MakeDefault();

	/**
	 * Creates a new instance of injury.
	 */
	IInjury MakeNew(PlayerEntity player);

	/**
	 * When injury is applied the first time to a player.
	 */
	void OnStartInjury();

	/**
	 * When an injury is removed from a player. Not when used by a command.
	 */
	void OnRemoveInjury();

	/**
	 * When a injury should process and update.
	 */
	void OnTickInjury();

	/**
	 * Injuries Identifier
	 */
	Identifier GetIdentifier();

	/**
	 * Duration remaining in ticks;
	 */
	int GetDuration();

	/**
	 * Get player of the Injury
	 */
	PlayerEntity GetPlayer();

	/**
	 * Sets duration in ticks
	 */
	void SetDuration(int dur);

	/**
	 * Returns description for the injuries tooltip
	 */
	Text[] GetDescription();

	/**
	 * Source of damage if any.
	 */
	DamageSource GetDamageSource();

	void Write(NbtCompound tag);

	void Read(NbtCompound tag);
}
