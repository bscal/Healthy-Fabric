package me.bscal.healthy.common.components.health;

import me.bscal.healthy.Healthy;
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
		return Healthy.GetServer().getTicks() % Math.max(1, ticksPerUpdate) == 0;
	}

	@Override
	public void Update(LivingEntity entity)
	{
		if (!finished)
		{
			entity.heal(healingPerUpdate);
			UpdateDuration(entity);
		}

		if (remainingDuration <= 0)
			finished = true;
	}

	@Override
	public void UpdateDuration(LivingEntity entity)
	{
		remainingDuration -= ticksPerUpdate;
	}

	@Override
	public void Write(CompoundTag tag)
	{
		duration = tag.getInt("duration");
		remainingDuration = tag.getInt("remainingDuration");
		ticksPerUpdate = tag.getInt("ticksPerUpdate");
		totalHealing = tag.getFloat("totalHealing");
		healingPerUpdate = tag.getFloat("healingPerUpdate");
	}

	@Override
	public void Read(CompoundTag tag)
	{
		tag.putInt("duration", duration);
		tag.putInt("remainingDuration", remainingDuration);
		tag.putInt("ticksPerUpdate", ticksPerUpdate);
		tag.putFloat("totalHealing", totalHealing);
		tag.putFloat("healingPerUpdate", healingPerUpdate);
	}
}
