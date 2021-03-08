package me.bscal.healthy.common.components.health;

import me.bscal.healthy.common.components.buff.IBuff;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundTag;

import java.util.Objects;

public class Heal implements IBuff
{

	public static final Heal ZERO = new Heal();

	public int duration;
	public int remainingDuration;
	public int ticksPerUpdate;
	public boolean finished;
	public float totalHealing;
	public float healingPerUpdate;

	public static Heal Builder(float totalHealing, int durInTicks, int ticksPerHeal)
	{
		Heal heal = new Heal();
		heal.duration = durInTicks;
		heal.remainingDuration = durInTicks;
		heal.ticksPerUpdate = ticksPerHeal;
		heal.totalHealing = totalHealing;
		heal.healingPerUpdate = totalHealing / (float)(durInTicks / ticksPerHeal);
		return heal;
	}

	@Override
	public int GetDuration()
	{
		return duration;
	}

	@Override
	public int GetRemainingDuration()
	{
		return remainingDuration;
	}

	@Override
	public int GetTicksPerUpdate()
	{
		return ticksPerUpdate;
	}

	@Override
	public boolean IsGood()
	{
		return true;
	}

	@Override
	public boolean IsFinished()
	{
		return finished;
	}

	@Override
	public boolean CanUpdate(LivingEntity entity)
	{
		return Objects.requireNonNull(entity.getServer()).getTicks() % ticksPerUpdate == 0 && remainingDuration > 0;
	}

	@Override
	public void Update(LivingEntity entity)
	{
		UpdateDuration(entity);

		entity.heal(healingPerUpdate);
	}

	@Override
	public void UpdateDuration(LivingEntity entity)
	{
		remainingDuration -= ticksPerUpdate;
	}

	@Override
	public void Write(CompoundTag tag)
	{
		duration = tag.getInt("heal_duration");
		remainingDuration = tag.getInt("heal_durationRemaining");
		ticksPerUpdate = tag.getInt("heal_ticksPerUpdate");
		totalHealing = tag.getFloat("heal_totalHealing");
		healingPerUpdate = tag.getFloat("heal_healingPerUpdate");
	}

	@Override
	public void Read(CompoundTag tag)
	{
		tag.putBoolean("heal", true);
		tag.putInt("heal_duration", duration);
		tag.putInt("heal_durationRemaining", remainingDuration);
		tag.putInt("heal_ticksPerUpdate", ticksPerUpdate);
		tag.putFloat("heal_totalHealing", totalHealing);
		tag.putFloat("heal_healingPerUpdate", healingPerUpdate);
	}
}
